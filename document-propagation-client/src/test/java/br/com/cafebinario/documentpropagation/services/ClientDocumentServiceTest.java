package br.com.cafebinario.documentpropagation.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.documentpropagation.configurations.TestConfiguration;
import br.com.cafebinario.documentpropagation.core.DocumentCatalogResolver;
import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.runners.SyncronizedDocuments;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestConfiguration.class })
@ActiveProfiles("test")
public class ClientDocumentServiceTest {

	@Autowired
	private ClientDocumentService clientDocumentService;

	@Autowired
	private DocumentCatalogResolver documentCatalogResolver;

	@MockBean
	private DocumentCatalogService documentCatalogService;
	
	@MockBean
	private SyncronizedDocuments syncronizedDocuments;

	@Test
	public void load() {

		final DocumentDTO newDocument = DocumentDTO.builder().content("new").build();
		final DocumentDTO oldDocument = DocumentDTO.builder().content("old").build();
		
		Mockito.when(syncronizedDocuments.getLocalDocument()) //
				.thenReturn(newDocument);

		Mockito.when(documentCatalogService.putDocument(documentCatalogResolver.getCatalogName(), newDocument))
		.thenReturn(oldDocument);
		
		final DocumentDTO document = clientDocumentService.load(documentCatalogResolver);
		
		assertEquals(oldDocument, document);
	}
}
