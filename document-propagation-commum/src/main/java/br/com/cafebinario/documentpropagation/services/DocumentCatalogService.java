package br.com.cafebinario.documentpropagation.services;

import java.math.BigInteger;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.hazelcast.core.HazelcastInstance;

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;
import br.com.cafebinario.logger.Log;

@Component
public class DocumentCatalogService {

	@Autowired
	private HazelcastInstance hazelcastInstance;

	@Log
	public Map<RefDocumentDTO, DocumentDTO> getDocumentCatalog(final String catalogName) {

		Assert.hasText(catalogName, "catalogName must not be empty.");

		return hazelcastInstance.getMap(catalogName);
	}

	@Log
	public DocumentDTO putDocument(final String catalogName, final DocumentDTO document) {

		Assert.notNull(document, "The document must not be null");

		return getDocumentCatalog(catalogName) //
				.put(document.getDocumentIdentifier(), document);
	}

	@Log
	public DocumentDTO remove(final String catalogName, final RefDocumentDTO refDocument) {

		Assert.notNull(refDocument, "The refDocument must not be null");

		return getDocumentCatalog(catalogName).remove(refDocument);
	}

	protected void registerDocumentInstance(final RefDocumentDTO refDocument) {

		hazelcastInstance //
				.getMap("balanceMap") //
				.set(refDocument.getName(), BigInteger.ZERO);
	}
}
