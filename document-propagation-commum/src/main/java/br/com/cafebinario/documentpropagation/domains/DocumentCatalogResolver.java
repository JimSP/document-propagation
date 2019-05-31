package br.com.cafebinario.documentpropagation.domains;

import java.util.List;

public interface DocumentCatalogResolver {

	String getCatalogName();
	
	List<String> getNestedNames(final String catalogName);
}
