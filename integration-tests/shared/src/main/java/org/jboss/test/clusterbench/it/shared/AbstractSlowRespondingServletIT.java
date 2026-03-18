/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.clusterbench.it.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.test.clusterbench.common.ClusterBenchConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Smoke test for {@link org.jboss.test.clusterbench.web.debug.SlowRespondingServlet}.
 *
 * @author Radoslav Husar
 */
@ExtendWith(ArquillianExtension.class)
@RunAsClient
public abstract class AbstractSlowRespondingServletIT {

    @Test
    public void test() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8080/clusterbench/slowresponding?" + ClusterBenchConstants.MILLISECONDS + "=1000");

            httpClient.execute(httpGet, response -> {
                assertEquals(200, response.getCode());

                String responseBody = EntityUtils.toString(response.getEntity());
                assertEquals(ClusterBenchConstants.SUCCESS, responseBody);

                return null;
            });
        }
    }
}
