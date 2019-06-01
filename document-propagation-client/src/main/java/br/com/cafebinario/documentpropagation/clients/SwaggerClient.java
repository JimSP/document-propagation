package br.com.cafebinario.documentpropagation.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SwaggerClient {

	private static final String LOCAL_HOST = "http://localhost";

	private static final String EMPTY = "";

	@Value("${server.port}")
	private String serverPort;

	public String getDocument(final String swaggerApiPath) {
		
		final String url = LOCAL_HOST + ":" + serverPort + "/" + swaggerApiPath;
		
		try {
			final RestTemplate restTemplate = new RestTemplate();
			return restTemplate.getForObject(url, String.class);
		}catch (HttpClientErrorException e) {
			log.warn("m=getDocument, url={}", url, e);
			return EMPTY;
		}	
	}
}
