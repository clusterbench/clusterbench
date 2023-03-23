package org.jboss.test.clusterbench.ear;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

import org.apache.http.HttpEntity;
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
public class DebugServletIT {

    public static final String JBOSS_NODE_NAME = "clusterbench-1";

    /**
     * Verifies jboss.node.name is obtainable from debug servlet.
     */
    @Test
    public void test() throws Exception {
        CloseableHttpClient hc = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8080/cluster FAIL bench/debug");

        try (CloseableHttpResponse response = hc.execute(httpGet)) {
            Assert.assertEquals(200, response.getStatusLine().getStatusCode());
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
                Assert.assertEquals(JBOSS_NODE_NAME, optionalNodeName.get());
            } else {
                Assert.fail();
            }

            EntityUtils.consume(entity);
        }
    }

}