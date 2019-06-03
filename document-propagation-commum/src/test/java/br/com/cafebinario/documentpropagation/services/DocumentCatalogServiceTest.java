package br.com.cafebinario.documentpropagation.services;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import br.com.cafebinario.documentpropagation.configurations.TestConfiguration;
import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestConfiguration.class })
public class DocumentCatalogServiceTest {

	private static final String CATALOG_NAME = "TEST";

	@Autowired
	private DocumentCatalogService documentCatalogService;

	@MockBean
	private HazelcastInstance hazelcastInstance;

	@MockBean
	private IMap<Object, Object> iMap;

	@Before
	public void init() {

		Mockito.when(hazelcastInstance.getMap(CATALOG_NAME)).thenReturn(iMap);
	}

	@Test
	public void getDocumentCatalog() {

		final Map<RefDocumentDTO, DocumentDTO> map = documentCatalogService.getDocumentCatalog(CATALOG_NAME);

		assertEquals(iMap, map);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getDocumentCatalogWithNull() {
		
		documentCatalogService.getDocumentCatalog(null);
	}

	@Test
	public void putDocument() {

		final DocumentDTO expected = DocumentDTO.builder().build();

		final DocumentDTO document = document();

		Mockito.when(iMap.put(document.getDocumentIdentifier(), document)) //
				.thenReturn(expected);

		final DocumentDTO result = documentCatalogService.putDocument(CATALOG_NAME, document);

		assertEquals(expected, result);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void putDocumentWithNull() {

		documentCatalogService.putDocument(CATALOG_NAME, null);
	}

	@Test
	public void remove() {

		final DocumentDTO expected = DocumentDTO.builder().build();

		final RefDocumentDTO documentIdentifier = documentIdentifier();

		Mockito.when(iMap.remove(documentIdentifier)) //
				.thenReturn(expected);

		final DocumentDTO result = documentCatalogService.remove(CATALOG_NAME, documentIdentifier);

		assertEquals(expected, result);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void removeWithNull() {

		documentCatalogService.remove(CATALOG_NAME, null);
	}

	private DocumentDTO document() {

		return DocumentDTO //
				.builder() //
				.content("content") //
				.documentIdentifier( //
						documentIdentifier()) //
				.lastUpdate(LocalDateTime.now()) //
				.url("url") //
				.build();
	}

	private RefDocumentDTO documentIdentifier() {

		return RefDocumentDTO //
				.builder() //
				.name("name") //
				.build();
	}
}
