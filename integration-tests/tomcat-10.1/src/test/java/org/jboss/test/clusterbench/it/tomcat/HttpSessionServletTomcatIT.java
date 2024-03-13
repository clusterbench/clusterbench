/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.it.tomcat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.clusterbench.web.session.HttpSessionServlet;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Radoslav Husar
 */
@RunWith(Arquillian.class)
@RunAsClient
public class HttpSessionServletTomcatIT {

    @Deployment(testable = false)
    public static Archive<?> deployment() {
        return ShrinkWrap
                .create(ZipImporter.class, "clusterbench.war")
                .importFrom(new File("target/clusterbench-ee10-web-tomcat.war"))
                .as(WebArchive.class);
    }

    @Test
    public void test(@ArquillianResource(HttpSessionServlet.class) URL baseURL) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(baseURL.toURI() + "/session");
            System.out.println(httpGet);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                assertEquals(200, response.getStatusLine().getStatusCode());

                String responseBody = EntityUtils.toString(response.getEntity());
                assertEquals("0", responseBody);

                // Also ensure session is created
                Optional<Header> header = Arrays.stream(response.getAllHeaders()).filter(h -> Arrays.stream(h.getElements()).anyMatch(e -> e.getName().equals("JSESSIONID"))).findAny();
                assertTrue(header.isPresent());
            }

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                assertEquals(200, response.getStatusLine().getStatusCode());

                String responseBody = EntityUtils.toString(response.getEntity());
                assertEquals("1", responseBody);
            }
        }
    }
}
