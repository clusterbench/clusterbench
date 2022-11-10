package org.jboss.test.clusterbench.ear;

import java.util.Arrays;
import java.util.Optional;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Radoslav Husar
 */
public class NodeNameServletIT {

    @Test
    public void test() throws Exception {
        try (CloseableHttpClient hc = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8080/clusterbench/jboss-node-name");

            try (CloseableHttpResponse response = hc.execute(httpGet)) {
                Assert.assertEquals(200, response.getStatusLine().getStatusCode());

                String responseBody = EntityUtils.toString(response.getEntity());
                Assert.assertEquals(DebugServletIT.JBOSS_NODE_NAME, responseBody);

                // Also ensure session is created with ?create=true
                Optional<Header> header = Arrays.stream(response.getAllHeaders()).filter(h -> Arrays.stream(h.getElements()).anyMatch(e -> e.getName().equals("JSESSIONID"))).findAny();
                Assert.assertFalse(header.isPresent());
            }

            httpGet = new HttpGet("http://localhost:8080/clusterbench/jboss-node-name?create=true");

            try (CloseableHttpResponse response = hc.execute(httpGet)) {
                Assert.assertEquals(200, response.getStatusLine().getStatusCode());

                String responseBody = EntityUtils.toString(response.getEntity());
                Assert.assertEquals(DebugServletIT.JBOSS_NODE_NAME, responseBody);

                // Also ensure session is created with ?create=true
                Optional<Header> header = Arrays.stream(response.getAllHeaders()).filter(h -> Arrays.stream(h.getElements()).anyMatch(e -> e.getName().equals("JSESSIONID"))).findAny();
                Assert.assertTrue(header.isPresent());
            }
        }
    }

}