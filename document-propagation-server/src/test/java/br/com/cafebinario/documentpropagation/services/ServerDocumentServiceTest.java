package br.com.cafebinario.documentpropagation.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ServerDocumentServiceTest {

	private static final String EMPTY = "";
	private static final String NAME = "name";
	private static final String CATALOG_NAME = "catalogName";

	@Autowired
	private ServerDocumentService serverDocumentService;

	@Test
	public void getDocumentByName() {

		final String document = serverDocumentService.getDocumentByName(CATALOG_NAME, NAME);

		assertEquals(EMPTY, document);	
	}

	@Test
	public void listAllDocuments() {

		final Collection<DocumentDTO> documents = serverDocumentService.listAllDocuments(CATALOG_NAME);

		assertThat(documents.isEmpty(), IsEqual.equalTo(Boolean.TRUE));
	}
	
	@Test
	public void listAllRefs() {
		
		final Collection<RefDocumentDTO> documents = serverDocumentService.listAllRefs();
		
		assertNotNull(documents);
	}
}
