package br.com.cafebinario.documentpropagation.core;

import java.util.List;

public interface DocumentCatalogResolver {

	String getCatalogName();
	
	List<String> getNestedNames(final String catalogName);
}
