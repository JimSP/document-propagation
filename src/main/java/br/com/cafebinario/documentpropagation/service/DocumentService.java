package br.com.cafebinario.documentpropagation.service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;
import br.com.cafebinario.documentpropagation.runners.SyncronizedDocuments;

@Service
public class DocumentService {
	
	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	@Autowired
	private SyncronizedDocuments syncronizedDocuments;

	public Collection<RefDocumentDTO> listAllRefs() {
		
		final Map<RefDocumentDTO, DocumentDTO> documentCatalog = getDocumentCatalog();
		
		return documentCatalog.keySet();
	}

	public Collection<DocumentDTO> listAllDocuments() {
		
		final Map<RefDocumentDTO, DocumentDTO> documentCatalog = getDocumentCatalog();
		
		return documentCatalog.values();
	}
	
	public Collection<DocumentDTO> getDocumentByName(final String name) {
		
		final Map<RefDocumentDTO, DocumentDTO> documentCatalog = getDocumentCatalog();
		
		return documentCatalog //
				.entrySet() //
				.stream() //
				.filter(predicate->name.equalsIgnoreCase(predicate.getKey().getName())) //
				.map(Map.Entry::getValue) //
				.collect(Collectors.toList());
	}

	public DocumentDTO load() {

		final DocumentDTO document = syncronizedDocuments.getLocalDocument();

		final Map<RefDocumentDTO, DocumentDTO> documentCatalog = getDocumentCatalog();

		return documentCatalog.put(document.getDocumentIdentifier(), document);
	}
	
	private Map<RefDocumentDTO, DocumentDTO> getDocumentCatalog() {
		return hazelcastInstance.getMap(SyncronizedDocuments.DOCUMENT_CATALOG);
	}
}
