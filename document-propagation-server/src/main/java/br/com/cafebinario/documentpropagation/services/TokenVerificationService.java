package br.com.cafebinario.documentpropagation.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cafebinario.documentpropagation.core.DocumentCatalogResolver;
import br.com.cafebinario.documentpropagation.domains.DocumentCatalog;

@Service
public class TokenVerificationService {

	@Value("#{${br.com.cafebinario.documentproragation.tokens}}")
	private Map<String, String> tokens;

	public List<String> lookupCatalog(final String token) {

		final DocumentCatalogResolver documentCatalogResolver = getDocumentCatalogResolver(token);

		final String catalogName = documentCatalogResolver.getCatalogName();

		return documentCatalogResolver.getNestedNames(catalogName);
	}

	private DocumentCatalogResolver getDocumentCatalogResolver(final String token) {

		return tokens.containsKey(token) ? DocumentCatalog.valueOf(tokens.get(token)) : DocumentCatalog.PUBLIC;
	}
}
