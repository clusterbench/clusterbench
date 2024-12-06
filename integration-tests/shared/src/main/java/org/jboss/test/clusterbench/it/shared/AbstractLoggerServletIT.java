/**
 * @author Tommaso Borgato
 * @author Radoslav Husar
 */
package org.jboss.test.clusterbench.it.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Tommaso Borgato
 * @author Radoslav Husar
 */
@ExtendWith(ArquillianExtension.class)
@RunAsClient
public abstract class AbstractLoggerServletIT {

    /**
     * Verifies log servlet is deployed
     */
    @Test
    public void test() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8080/clusterbench/log?msg=Basic%20info.");

            httpClient.execute(httpGet, response -> {
                assertEquals(200, response.getCode());
                String responseBody = EntityUtils.toString(response.getEntity());
                assertEquals("Success", responseBody);

                return null;
            });

            // Test providing a level
            httpGet = new HttpGet("http://localhost:8080/clusterbench/log?level=WARN&msg=Test%20warning.");
            httpClient.execute(httpGet, response -> {
                assertEquals(200, response.getCode());
                String responseBody = EntityUtils.toString(response.getEntity());
                assertEquals("Success", responseBody);

                return null;
            });

            // Test providing non-existent level and no message should default to "INFO: ping".
            httpGet = new HttpGet("http://localhost:8080/clusterbench/log?level=WEIRD");
            httpClient.execute(httpGet, response -> {
                assertEquals(200, response.getCode());
                String responseBody = EntityUtils.toString(response.getEntity());
                assertEquals("Success", responseBody);

                return null;
            });
        }

    }
}
