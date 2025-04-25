/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.clusterbench.it.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Verifies jboss.node.name or Runtime.getRuntime().availableProcessors() is obtainable from the debug servlet.
 * Intended to cover other properties as well.
 *
 * @author Radoslav Husar
 */
@ExtendWith(ArquillianExtension.class)
@RunAsClient
public abstract class AbstractDebugServletIT {

    public static final String JBOSS_NODE_NAME = "clusterbench-1";

    @Test
    public void test() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8080/clusterbench/debug");

            httpClient.execute(httpGet, response -> {
                assertEquals(200, response.getCode());

                HttpEntity entity = response.getEntity();

                Map<String, String> values = new BufferedReader(new InputStreamReader(entity.getContent()))
                        .lines()
                        // Skip duplicated request headers and empty lines
                        .filter(line -> line.contains(":") && !line.startsWith("Request header:") && !line.trim().isEmpty())
                        .map(line -> line.split(":", 2))
                        .collect(Collectors.toMap(segment -> segment[0].trim(), segment -> segment[1].trim()));

                check(values, "Node name", value -> assertEquals(JBOSS_NODE_NAME, value));
                check(values, "Runtime.getRuntime().availableProcessors()", value -> assertEquals(Integer.parseInt(value), Runtime.getRuntime().availableProcessors()));

                return null;
            });
        }
    }

    private static void check(Map<String, String> values, String key, Consumer<String> consumer) {
        assertTrue(values.containsKey(key), "Missing key: " + key);
        consumer.accept(values.get(key));
    }
}