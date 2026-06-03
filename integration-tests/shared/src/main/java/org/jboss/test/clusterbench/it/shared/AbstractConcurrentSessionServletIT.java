/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.clusterbench.it.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Parameterized integration test for concurrent access to all session-stateful servlets.
 * Subclasses provide the list of servlet paths to test via a static {@code servletPaths()} method.
 *
 * @author Radoslav Husar
 */
@ExtendWith(ArquillianExtension.class)
@RunAsClient
public abstract class AbstractConcurrentSessionServletIT {

    private static final Logger log = Logger.getLogger(AbstractConcurrentSessionServletIT.class.getName());

    @ParameterizedTest
    @MethodSource("servletPaths")
    public void concurrent(String servletPath) throws Exception {
        int numberOfBatches = 10;
        int concurrentRequestsPerBatch = 10;
        int totalRequests = numberOfBatches * concurrentRequestsPerBatch;

        ExecutorService executorService = Executors.newFixedThreadPool(concurrentRequestsPerBatch);

        Set<Integer> observedValues = ConcurrentHashMap.newKeySet();
        AtomicInteger errorCount = new AtomicInteger(0);

        String url = "http://localhost:8080/clusterbench" + servletPath;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // First, establish a session with an initial request
            httpClient.execute(new HttpGet(url), response -> {
                assertEquals(200, response.getCode());
                String responseBody = EntityUtils.toString(response.getEntity());
                assertEquals("0", responseBody);
                observedValues.add(0);

                // Ensure session is created
                Optional<String> sessionIdCookie = Arrays.stream(response.getHeaders("Set-Cookie"))
                        .map(Header::getValue)
                        .filter(cookie -> cookie.startsWith("JSESSIONID="))
                        .findFirst();
                assertTrue(sessionIdCookie.isPresent(), "Session cookie should be present");

                return null;
            });

            // Run sequential batches of concurrent requests
            long startTime = System.nanoTime();

            for (int batch = 0; batch < numberOfBatches; batch++) {
                List<Future<?>> futures = new ArrayList<>(concurrentRequestsPerBatch);

                for (int j = 0; j < concurrentRequestsPerBatch; j++) {
                    futures.add(executorService.submit(() -> {
                        try {
                            httpClient.execute(new HttpGet(url), response -> {
                                if (response.getCode() == 200) {
                                    String responseBody = EntityUtils.toString(response.getEntity());
                                    observedValues.add(Integer.parseInt(responseBody));
                                } else {
                                    errorCount.incrementAndGet();
                                }
                                return null;
                            });
                        } catch (Exception e) {
                            errorCount.incrementAndGet();
                            log.log(Level.SEVERE, "Error during concurrent request execution", e);
                        }
                    }));
                }

                // Wait for all concurrent requests in this batch to complete before the next batch
                for (Future<?> future : futures) {
                    future.get(30, TimeUnit.SECONDS);
                }
            }

            long endTime = System.nanoTime();

            long durationMs = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
            log.info(String.format("Concurrent execution of %s completed: %d batches x %d concurrent requests = %d total requests in %d ms",
                    servletPath, numberOfBatches, concurrentRequestsPerBatch, totalRequests, durationMs));

            // Shutdown executor
            executorService.shutdown();
            assertTrue(executorService.awaitTermination(10, TimeUnit.SECONDS));

            // Verify results
            assertEquals(0, errorCount.get(), "There should be no errors for " + servletPath);
            // Total requests is initial request (1) + concurrent requests (numberOfBatches * concurrentRequestsPerBatch)
            int expectedTotalRequests = 1 + totalRequests;
            assertEquals(expectedTotalRequests, observedValues.size(), "All values should be unique for " + servletPath);

            // Verify values are in expected range [0..expectedTotalRequests-1]
            Set<Integer> expectedValues = IntStream.range(0, expectedTotalRequests).boxed().collect(Collectors.toSet());
            assertEquals(expectedValues, observedValues, "Observed values should match expected range for " + servletPath);
        }
    }
}
