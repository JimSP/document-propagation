package br.com.cafebinario.documentpropagation.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;

@Component
public class DocumentCatalogService {

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	public Map<RefDocumentDTO, DocumentDTO> getDocumentCatalog(final String catalogName) {
		return hazelcastInstance.getMap(catalogName);
	}

	public DocumentDTO putDocument(final String catalogName, final DocumentDTO document) {
		return getDocumentCatalog(catalogName) //
				.put(document.getDocumentIdentifier(), document);
	}
	
	public DocumentDTO remove(final String catalogName, final RefDocumentDTO refDocumentDTO) {
		return getDocumentCatalog(catalogName).remove(refDocumentDTO);
	}
}
