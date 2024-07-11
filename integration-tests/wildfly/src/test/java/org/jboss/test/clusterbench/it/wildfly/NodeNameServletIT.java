/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.it.wildfly;

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
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Radoslav Husar
 */
@ExtendWith(ArquillianExtension.class)
@RunAsClient
public class NodeNameServletIT extends AbstractWildFlyIT {

    @Test
    public void test() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8080/clusterbench/jboss-node-name");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                assertEquals(200, response.getStatusLine().getStatusCode());

                String responseBody = EntityUtils.toString(response.getEntity());
                assertEquals(DebugServletIT.JBOSS_NODE_NAME, responseBody);

                // Also ensure session is created with ?create=true
                Optional<Header> header = Arrays.stream(response.getAllHeaders()).filter(h -> Arrays.stream(h.getElements()).anyMatch(e -> e.getName().equals("JSESSIONID"))).findAny();
                Assertions.assertFalse(header.isPresent());
            }

            httpGet = new HttpGet("http://localhost:8080/clusterbench/jboss-node-name?create=true");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                assertEquals(200, response.getStatusLine().getStatusCode());

                String responseBody = EntityUtils.toString(response.getEntity());
                assertEquals(DebugServletIT.JBOSS_NODE_NAME, responseBody);

                // Also ensure session is created with ?create=true
                Optional<Header> header = Arrays.stream(response.getAllHeaders()).filter(h -> Arrays.stream(h.getElements()).anyMatch(e -> e.getName().equals("JSESSIONID"))).findAny();
                assertTrue(header.isPresent());
            }
        }
    }

}