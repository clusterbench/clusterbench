/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.it.wildfly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.jboss.arquillian.junit5.ArquillianExtension;

/**
 * @author Radoslav Husar
 */
@ExtendWith(ArquillianExtension.class)
@RunAsClient
public class DebugServletIT extends AbstractWildFlyIT {

    public static final String JBOSS_NODE_NAME = "clusterbench-1";

    /**
     * Verifies jboss.node.name is obtainable from debug servlet.
     */
    @Test
    public void test() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8080/clusterbench/debug");


            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                assertEquals(200, response.getStatusLine().getStatusCode());
                HttpEntity entity = response.getEntity();

                Optional<String> optionalNodeName = new BufferedReader(new InputStreamReader(entity.getContent()))
                        .lines()
                        .filter(x -> x.startsWith("Node name:"))
                        .map(x -> {
                            String[] split = x.split(":");
                            return split[1].trim();
                        })
                        .findFirst();


                if (optionalNodeName.isPresent()) {
                    assertEquals(JBOSS_NODE_NAME, optionalNodeName.get());
                } else {
                    fail();
                }
            }
        }

    }
}