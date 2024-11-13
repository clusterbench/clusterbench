package org.jboss.test.clusterbench.it.wildfly;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ArquillianExtension.class)
@RunAsClient
public class LoggerServletIT extends AbstractWildFlyIT {
	/**
	 * Verifies log servlet is deployed
	 */
	@Test
	public void test() throws Exception {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet httpGet = new HttpGet("http://localhost:8080/clusterbench/log");
			try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
				assertEquals(200, response.getStatusLine().getStatusCode());
				String responseBody = EntityUtils.toString(response.getEntity());
				assertEquals("Success", responseBody);
			}
		}

	}
}
