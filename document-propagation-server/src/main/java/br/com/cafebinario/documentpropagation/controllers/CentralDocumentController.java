package br.com.cafebinario.documentpropagation.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;
import br.com.cafebinario.documentpropagation.service.ServerDocumentService;

@RestController
public class CentralDocumentController {
	
	@Autowired
	private ServerDocumentService serverDocumentService;

	@GetMapping(path="/documents", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Collection<RefDocumentDTO> getRefDocuments(){
		return serverDocumentService.listAllRefs();
	}
	
	@GetMapping(path="/documents/contents", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Collection<DocumentDTO> getDocuments(){
		return serverDocumentService.listAllDocuments();
	}
	
	@CrossOrigin
	@GetMapping(path="/documents/{name}/content", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody String getDocuments(@PathVariable(name="name", required=true) final String name){
		return serverDocumentService.getDocumentByName(name);
	}
}
