/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ear;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link org.jboss.test.clusterbench.web.debug.HttpResponseServlet}.
 *
 * @author Radoslav Husar
 */
public class HttpResponseServletIT {

    @Test
    public void test() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8080/clusterbench/http-response?code=200");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                assertEquals(200, response.getStatusLine().getStatusCode());

                String responseBody = EntityUtils.toString(response.getEntity());
                assertTrue(responseBody.contains("HTTP Code was: 200"));
                assertTrue(responseBody.contains("JVM route: " + DebugServletIT.JBOSS_NODE_NAME));
                assertTrue(responseBody.contains("Session isNew: true"));

                // Also ensure session is created
                Optional<Header> header = Arrays.stream(response.getAllHeaders()).filter(h -> Arrays.stream(h.getElements()).anyMatch(e -> e.getName().equals("JSESSIONID"))).findAny();
                assertTrue(header.isPresent());
            }

            httpGet = new HttpGet("http://localhost:8080/clusterbench/http-response?code=503");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                assertEquals(503, response.getStatusLine().getStatusCode());

                String responseBody = EntityUtils.toString(response.getEntity());
                assertTrue(responseBody.contains("HTTP Code was: 503"));
                assertTrue(responseBody.contains("JVM route: " + DebugServletIT.JBOSS_NODE_NAME));
                assertTrue(responseBody.contains("Session isNew: false"));
            }
        }
    }

}
