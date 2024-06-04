/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.it.tomcat;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.test.clusterbench.web.session.HttpSessionServlet;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tomcat version of the smoke test for {@link org.jboss.test.clusterbench.web.load.AverageSystemLoadServlet}.
 *
 * @author Radoslav Husar
 */
@RunWith(Arquillian.class)
@RunAsClient
public class AverageSystemLoadServletTomcatIT extends AbstractTomcatIT {

    @Test
    public void test(@ArquillianResource(HttpSessionServlet.class) URL baseURL) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(baseURL.toURI() + "/averagesystemload?milliseconds=1000&threads=4");
            System.out.println(httpGet);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                assertEquals(200, response.getStatusLine().getStatusCode());

                String responseBody = EntityUtils.toString(response.getEntity());
                assertEquals("DONE", responseBody);
            }
        }
    }
}
