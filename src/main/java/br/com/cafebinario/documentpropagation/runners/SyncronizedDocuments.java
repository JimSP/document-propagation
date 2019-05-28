package br.com.cafebinario.documentpropagation.runners;

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;

public interface SyncronizedDocuments {
	
	final String DOCUMENT_CATALOG = "documentCatalog";
	
	DocumentDTO getLocalDocument();
}
