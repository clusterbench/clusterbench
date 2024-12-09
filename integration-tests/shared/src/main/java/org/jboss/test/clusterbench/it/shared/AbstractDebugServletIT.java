/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.clusterbench.it.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Radoslav Husar
 */
@ExtendWith(ArquillianExtension.class)
@RunAsClient
public abstract class AbstractDebugServletIT {

    public static final String JBOSS_NODE_NAME = "clusterbench-1";

    /**
     * Verifies jboss.node.name is obtainable from debug servlet.
     */
    @Test
    public void test() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8080/clusterbench/debug");

            httpClient.execute(httpGet, response -> {
                assertEquals(200, response.getCode());

                HttpEntity entity = response.getEntity();

                Optional<String> optionalNodeName = new BufferedReader(new InputStreamReader(entity.getContent()))
                        .lines()
                        .filter(line -> line.startsWith("Node name:"))
                        .map(line -> {
                            String[] split = line.split(":");
                            return split[1].trim();
                        })
                        .findFirst();


                if (optionalNodeName.isPresent()) {
                    assertEquals(JBOSS_NODE_NAME, optionalNodeName.get());
                } else {
                    fail("Missing node name.");
                }

                return null;
            });
        }
    }
}