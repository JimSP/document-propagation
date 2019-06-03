package br.com.cafebinario.documentpropagation.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.hazelcast.core.HazelcastInstance;

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;

@Component
public class DocumentCatalogService {

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	public Map<RefDocumentDTO, DocumentDTO> getDocumentCatalog(final String catalogName) {
		
		Assert.hasText(catalogName, "catalogName must not be empty.");
		
		return hazelcastInstance.getMap(catalogName);
	}

	public DocumentDTO putDocument(final String catalogName, final DocumentDTO document) {
		
		Assert.notNull(document, "The document must not be null");
		
		return getDocumentCatalog(catalogName) //
				.put(document.getDocumentIdentifier(), document);
	}
	
	public DocumentDTO remove(final String catalogName, final RefDocumentDTO refDocument) {
		
		Assert.notNull(refDocument, "The refDocument must not be null");
		
		return getDocumentCatalog(catalogName).remove(refDocument);
	}
}
