package br.com.cafebinario.documentpropagation.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;
import br.com.cafebinario.documentpropagation.service.DocumentService;

@RestController()
public class CentalDocumentViewController {
	
	@Autowired
	private DocumentService documentService;

	@GetMapping(path="/documents", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Collection<RefDocumentDTO> getRefDocuments(){
		return documentService.listAllRefs();
	}
	
	@GetMapping(path="/documents/contents", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Collection<DocumentDTO> getDocuments(){
		return documentService.listAllDocuments();
	}
	
	@GetMapping(path="/documents/{name}/contents", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Collection<DocumentDTO> getDocuments(@PathVariable(name="name", required=true) final String name){
		return documentService.getDocumentByName(name);
	}
	
	@PostMapping(path="/documents", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code=HttpStatus.CREATED)
	public @ResponseBody DocumentDTO loadData() {
		return documentService.load();
	}
}
