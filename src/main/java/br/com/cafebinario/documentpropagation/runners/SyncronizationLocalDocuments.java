package br.com.cafebinario.documentpropagation.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.cafebinario.documentpropagation.service.DocumentService;

@Component
public class SyncronizationLocalDocuments implements CommandLineRunner {


	@Autowired
	private DocumentService documentService;

	@Override
	public void run(String... args) throws Exception {

		documentService.load();
	}
}
