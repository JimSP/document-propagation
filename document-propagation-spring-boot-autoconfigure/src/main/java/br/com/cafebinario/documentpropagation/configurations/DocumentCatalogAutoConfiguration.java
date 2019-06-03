package br.com.cafebinario.documentpropagation.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import br.com.cafebinario.documentpropagation.core.DocumentCatalogResolver;
import br.com.cafebinario.documentpropagation.domains.DocumentCatalog;

@Configuration
public class DocumentCatalogAutoConfiguration {

	@Bean
	public DocumentCatalogResolver documentCatalogResolver() {
		return DocumentCatalog.PUBLIC;
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
