package br.com.cafebinario.documentpropagation.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cafebinario.documentpropagation.clients.SwaggerClient;
import br.com.cafebinario.documentpropagation.core.DocumentCatalogResolver;
import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;
import br.com.cafebinario.documentpropagation.runners.SyncronizedDocuments;
import br.com.cafebinario.logger.Log;
import br.com.cafebinario.logger.VerboseMode;

@Service
public class SyncronizedDocumentsService implements SyncronizedDocuments {

	private static final String SWAGGER_PATH = "v2/api-docs";

	@Value("${server.port:8080}")
	private Integer serverPort;

	@Value("${spring.application.name:document-propagation}")
	private String applicationName;
	
	@Autowired
	private SwaggerClient swaggerClient;
	
	@Autowired
	private NetworkService networkService;
	
	@Autowired
	private DocumentCatalogService documentCatalogService;
	
	@Autowired
	private DocumentCatalogResolver documentCatalogResolver;

	@Log(verboseMode=VerboseMode.ON)
	@Override
	public DocumentDTO getLocalDocument() {

		final String hostName = networkService.getHostName();
		final String publicUrl = networkService.getUrl(hostName, serverPort, SWAGGER_PATH);
		final String name = getName(hostName);

		final RefDocumentDTO identifier = getIdentifier(name);
		final String content = getContent();

		return DocumentDTO //
				.builder() //
				.documentIdentifier(identifier) //
				.content(content) //
				.url(publicUrl) //
				.lastUpdate(LocalDateTime.now()) //
				.build();
	}

	@Log
	@Override
	public void destroy() throws Exception {
		
		final RefDocumentDTO documentIdentifier = getLocalDocument().getDocumentIdentifier();
		
		documentCatalogService.remove("balanceMap", documentIdentifier);
		
		documentCatalogService.remove(documentCatalogResolver.getCatalogName(), documentIdentifier);
	}
	
	protected String getName(final String hostName) {
		
		return applicationName + "-" + hostName + "-" + serverPort;
	}

	private RefDocumentDTO getIdentifier(final String name) {
		return RefDocumentDTO //
				.builder() //
				.name(name) //
				.build();
	}

	private String getContent() {
		return swaggerClient.getDocument(SWAGGER_PATH);
	}
}
