package br.com.cafebinario.documentpropagation.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.com.cafebinario.documentpropagation.core.DocumentCatalogResolver;
import br.com.cafebinario.documentpropagation.services.ClientDocumentService;
import br.com.cafebinario.logger.Log;

@Component
@Profile("!test")
public class SyncronizationLocalDocuments implements CommandLineRunner {

	@Autowired
	private ClientDocumentService clientDocumentService;
	
	@Autowired
	private DocumentCatalogResolver documentCatalogResolver;

	@Log
	@Override
	public void run(String... args) throws Exception {

		clientDocumentService.load(documentCatalogResolver);
	}
}
