/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.clusterbench.it.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Radoslav Husar
 */
@ExtendWith(ArquillianExtension.class)
@RunAsClient
public abstract class AbstractNodeNameServletIT {

    @Test
    public void test() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8080/clusterbench/jboss-node-name");

            httpClient.execute(httpGet, response -> {
                assertEquals(200, response.getCode());

                String responseBody = EntityUtils.toString(response.getEntity());
                assertEquals(AbstractDebugServletIT.JBOSS_NODE_NAME, responseBody);

                // Also ensure session is created with ?create=true

                // Check for the JSESSIONID cookie in the response headers
                Optional<String> jsessionIdCookie = Arrays.stream(response.getHeaders("Set-Cookie")).map(Header::getValue).filter(cookie -> cookie.startsWith("JSESSIONID=")).findFirst();
                assertFalse(jsessionIdCookie.isPresent());

                return null;
            });

            httpGet = new HttpGet("http://localhost:8080/clusterbench/jboss-node-name?create=true");

            httpClient.execute(httpGet, response -> {
                        assertEquals(200, response.getCode());

                        String responseBody = EntityUtils.toString(response.getEntity());
                        assertEquals(AbstractDebugServletIT.JBOSS_NODE_NAME, responseBody);

                        // Also ensure session is created with ?create=true
                        Optional<String> jsessionIdCookie = Arrays.stream(response.getHeaders("Set-Cookie")).map(Header::getValue).filter(cookie -> cookie.startsWith("JSESSIONID=")).findFirst();
                        assertTrue(jsessionIdCookie.isPresent());

                        return null;
                    }
            );
        }
    }

}