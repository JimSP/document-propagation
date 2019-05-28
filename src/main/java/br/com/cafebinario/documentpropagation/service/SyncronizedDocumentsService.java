package br.com.cafebinario.documentpropagation.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;
import br.com.cafebinario.documentpropagation.exceptions.CannotResolveDocumentName;
import br.com.cafebinario.documentpropagation.runners.SyncronizedDocuments;

@Service
public class SyncronizedDocumentsService implements SyncronizedDocuments {

	private static final String LOCALHOST = "localhost";
	private static final String SWAGGER_PATH = "v2/api-docs";

	@Value("${server.port:8080}")
	private Integer serverPort;

	@Value("${spring.application.name:document-propagation}")
	private String applicationName;

	@Override
	public DocumentDTO getLocalDocument() {

		final String hostName = getHostName();
		final String publicUrl = getUrl(hostName, serverPort, SWAGGER_PATH);
		final String privateUrl = getUrl(LOCALHOST, serverPort, SWAGGER_PATH);

		final RefDocumentDTO identifier = getIdentifier(applicationName, publicUrl);
		final String content = getContent(privateUrl);

		return DocumentDTO //
				.builder() //
				.documentIdentifier(identifier) //
				.content(content) //
				.lastUpdate(LocalDateTime.now()) //
				.build();
	}

	private String getHostName() {
		try {
			return InetAddress //
					.getLocalHost() //
					.getHostName();
		} catch (UnknownHostException ex) {
			throw new CannotResolveDocumentName(ex);
		}
	}

	private String getUrl(final String host, final Integer port, final String path) {
		return "http://" + host + ":" + port + "/" + path;
	}

	private RefDocumentDTO getIdentifier(final String name, final String url) {
		return RefDocumentDTO //
				.builder() //
				.name(name) //
				.url(url) //
				.build();
	}

	private String getContent(final String url) {
		final RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, String.class);
	}
}
