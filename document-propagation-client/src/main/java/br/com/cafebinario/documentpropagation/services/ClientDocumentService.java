package br.com.cafebinario.documentpropagation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cafebinario.documentpropagation.domain.DocumentCatalogResolver;
import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.runners.SyncronizedDocuments;
import br.com.cafebinario.documentpropagation.service.DocumentCatalogService;

@Service
public class ClientDocumentService {
	
	@Autowired
	private DocumentCatalogService documentCatalogService;
	
	@Autowired
	private SyncronizedDocuments syncronizedDocuments;

	public DocumentDTO load(final DocumentCatalogResolver documentCatalogResolver) {

		final DocumentDTO document = syncronizedDocuments.getLocalDocument();

		return documentCatalogService.putDocument(documentCatalogResolver.getCatalogName(), document);
	}
}