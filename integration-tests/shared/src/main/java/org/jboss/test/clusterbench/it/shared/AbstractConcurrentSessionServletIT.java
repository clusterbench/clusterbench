/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.clusterbench.it.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
        int numberOfThreads = 10;
        int requestsPerThread = 10;
        int totalRequests = numberOfThreads * requestsPerThread;

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(numberOfThreads);

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

            // Now submit concurrent tasks - all will use the same session established above
            for (int i = 0; i < numberOfThreads; i++) {
                executorService.submit(() -> {
                    try {
                        // Wait for all threads to be ready
                        startLatch.await();

                        for (int j = 0; j < requestsPerThread; j++) {
                            httpClient.execute(new HttpGet(url), response -> {
                                if (response.getCode() == 200) {
                                    String responseBody = EntityUtils.toString(response.getEntity());
                                    observedValues.add(Integer.parseInt(responseBody));
                                } else {
                                    errorCount.incrementAndGet();
                                }
                                return null;
                            });
                        }
                    } catch (Exception e) {
                        errorCount.incrementAndGet();
                        log.log(Level.SEVERE, "Error during concurrent request execution", e);
                    } finally {
                        doneLatch.countDown();
                    }
                });
            }

            // Start all threads at once and capture timing
            long startTime = System.nanoTime();
            startLatch.countDown();

            // Wait for all threads to complete
            assertTrue(doneLatch.await(30, TimeUnit.SECONDS), "Concurrent requests did not complete in time");
            long endTime = System.nanoTime();

            long durationMs = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
            log.info(String.format("Concurrent execution of %s completed: %d threads x %d requests = %d total requests in %d ms",
                    servletPath, numberOfThreads, requestsPerThread, totalRequests, durationMs));

            // Shutdown executor
            executorService.shutdown();
            assertTrue(executorService.awaitTermination(10, TimeUnit.SECONDS));

            // Verify results
            assertEquals(0, errorCount.get(), "There should be no errors for " + servletPath);
            // Total requests is initial request (1) + concurrent requests (numberOfThreads * requestsPerThread)
            int expectedTotalRequests = 1 + totalRequests;
            assertEquals(expectedTotalRequests, observedValues.size(), "All values should be unique for " + servletPath);

            // Verify values are in expected range [0..expectedTotalRequests-1]
            Set<Integer> expectedValues = IntStream.range(0, expectedTotalRequests).boxed().collect(Collectors.toSet());
            assertEquals(expectedValues, observedValues, "Observed values should match expected range for " + servletPath);
        }
    }
}
