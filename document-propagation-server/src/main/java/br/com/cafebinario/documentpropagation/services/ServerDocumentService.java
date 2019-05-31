package br.com.cafebinario.documentpropagation.services;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cafebinario.documentpropagation.domains.DocumentCatalogResolver;
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
	
	public Collection<RefDocumentDTO> listAllRefs(final String catalogName) {
		
		final Map<RefDocumentDTO, DocumentDTO> documentCatalog = getDocumentCatalog(catalogName);
		
		return documentCatalog.keySet();
	}

	public Collection<DocumentDTO> listAllDocuments(final String catalogName) {
		
		final Map<RefDocumentDTO, DocumentDTO> documentCatalog = getDocumentCatalog(catalogName);
		
		return documentCatalog.values();
	}
	
	public String getDocumentByName(final String catalogName, final String name) {
		
		final Map<RefDocumentDTO, DocumentDTO> documentCatalog = getDocumentCatalog(catalogName);
		
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
	
	private Map<RefDocumentDTO, DocumentDTO> getDocumentCatalog(final String catalogName) {
		
		return documentCatalogService.getDocumentCatalog(catalogName);
	}
	
	private Map<RefDocumentDTO, DocumentDTO> getDocumentCatalog() {
		
		return documentCatalogService.getDocumentCatalog(documentCatalogResolver.getCatalogName());
	}
}
