package br.com.cafebinario.documentpropagation.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SwaggerClient {

	private static final String LOCAL_HOST = "http://localhost";

	@Value("${server.port}")
	private String serverPort;

	public String getDocument(final String swaggerApiPath) {
		final RestTemplate restTemplate = new RestTemplate();	
		return restTemplate.getForObject(LOCAL_HOST + ":" + serverPort + "/" + swaggerApiPath, String.class);
		
	}
}
