package br.com.cafebinario.documentpropagation.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.cafebinario.documentpropagation.domain.DocumentCatalog;
import br.com.cafebinario.documentpropagation.domain.DocumentCatalogResolver;

@Configuration
public class DocumentCatalogConfiguration {

	@Bean
	public DocumentCatalogResolver documentCatalogResolver() {
		return DocumentCatalog.DEFAULT;
	}
}
