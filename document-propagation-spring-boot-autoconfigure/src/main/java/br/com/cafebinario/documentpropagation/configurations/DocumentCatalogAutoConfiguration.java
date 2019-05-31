package br.com.cafebinario.documentpropagation.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.cafebinario.documentpropagation.domains.DocumentCatalog;
import br.com.cafebinario.documentpropagation.domains.DocumentCatalogResolver;

@Configuration
public class DocumentCatalogAutoConfiguration {

	@Bean
	public DocumentCatalogResolver documentCatalogResolver() {
		return DocumentCatalog.PUBLIC;
	}
}
