package br.com.cafebinario.documentpropagation.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.cafebinario.documentpropagation.domain.DocumentCatalogResolver;
import br.com.cafebinario.documentpropagation.services.ClientDocumentService;

@Component
class SyncronizationLocalDocuments implements CommandLineRunner {

	@Autowired
	private ClientDocumentService clientDocumentService;
	
	@Autowired
	private DocumentCatalogResolver documentCatalogResolver;

	@Override
	public void run(String... args) throws Exception {

		clientDocumentService.load(documentCatalogResolver);
	}
}
