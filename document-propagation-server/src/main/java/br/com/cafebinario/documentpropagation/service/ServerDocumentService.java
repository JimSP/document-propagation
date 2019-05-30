package br.com.cafebinario.documentpropagation.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cafebinario.documentpropagation.domain.DocumentCatalogResolver;
import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;

@Service
public class ServerDocumentService {
	
	private static final String EMPTY = "";

	@Autowired
	private DocumentCatalogService documentCatalogService;
	
	@Autowired
	private DocumentCatalogResolver documentCatalogResolver;

	public Collection<RefDocumentDTO> listAllRefs() {
		
		final Map<RefDocumentDTO, DocumentDTO> documentCatalog = getDocumentCatalog();
		
		return documentCatalog.keySet();
	}

	public Collection<DocumentDTO> listAllDocuments() {
		
		final Map<RefDocumentDTO, DocumentDTO> documentCatalog = getDocumentCatalog();
		
		return documentCatalog.values();
	}
	
	public String getDocumentByName(final String name) {
		
		final Map<RefDocumentDTO, DocumentDTO> documentCatalog = getDocumentCatalog();
		
		return documentCatalog //
				.entrySet() //
				.stream() //
				.filter(predicate->name.equalsIgnoreCase(predicate.getKey().getName())) //
				.map(Map.Entry::getValue) //
				.findFirst()
				.orElse(DocumentDTO
						.builder()
						.content(EMPTY)
						.build())
				.getContent();
	}
	
	private Map<RefDocumentDTO, DocumentDTO> getDocumentCatalog() {
		
		return documentCatalogService.getDocumentCatalog(documentCatalogResolver.getCatalogName());
	}
}
