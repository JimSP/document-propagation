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

import br.com.cafebinario.documentpropagation.clients.SwaggerClient;
import br.com.cafebinario.documentpropagation.configurations.TestConfiguration;
import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestConfiguration.class })
@ActiveProfiles("test")
public class SyncronizedDocumentsServiceTest {

	private static final int PORT = 5421;

	private static final String HOST = "host";

	private static final String HOST_NAME = "hostName";

	private static final String CONTENT = "content";

	private static final String PATH = "path";

	@Autowired
	private SyncronizedDocumentsService syncronizedDocumentsService;

	@MockBean
	private SwaggerClient swaggerClient;

	@MockBean
	private NetworkService networkService;

	@Test
	public void test() {

		Mockito //
				.when(swaggerClient.getDocument(PATH)) //
				.thenReturn(CONTENT);

		Mockito //
				.when(networkService.getHostName()) //
				.thenReturn(HOST_NAME);

		Mockito //
				.when(networkService.getUrl(HOST, PORT, PATH)) //
				.thenReturn(HOST_NAME);

		final String name = syncronizedDocumentsService.getName(HOST_NAME);

		final DocumentDTO localDocument = syncronizedDocumentsService.getLocalDocument();

		assertEquals(document(name), localDocument);
	}

	private DocumentDTO document(final String name) {
		return DocumentDTO //
				.builder() //
				.documentIdentifier(documentIdentifier(name)) //
				.build();
	}

	private RefDocumentDTO documentIdentifier(final String name) {
		return RefDocumentDTO //
				.builder() //
				.name(name) //
				.build();
	}
}
