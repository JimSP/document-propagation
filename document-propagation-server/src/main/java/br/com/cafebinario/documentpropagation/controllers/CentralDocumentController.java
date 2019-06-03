package br.com.cafebinario.documentpropagation.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;
import br.com.cafebinario.documentpropagation.services.ServerDocumentService;
import br.com.cafebinario.documentpropagation.services.TokenVerificationService;

@RestController
public class CentralDocumentController {

	private static final String EMPTY = "{}";

	@Autowired
	private ServerDocumentService serverDocumentService;

	@Autowired
	private TokenVerificationService tokenVerificationService;

	@GetMapping(path = "/documents", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Collection<RefDocumentDTO> getRefDocuments(@RequestHeader(name = "token", required = false) final String token) {

		final List<String> nestedNames = lookupNames(token);

		return nestedNames //
				.stream() //
				.flatMap(name -> serverDocumentService.listAllRefs(name).stream()) //
				.collect(Collectors.toList());
	}

	@GetMapping(path = "/documents/contents", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Collection<DocumentDTO> getContentDocuments(@RequestHeader(name = "token", required = false) final String token) {

		final List<String> nestedNames = lookupNames(token);

		return nestedNames //
				.stream() //
				.flatMap(name -> serverDocumentService.listAllDocuments(name).stream()) //
				.collect(Collectors.toList());
	}

	@CrossOrigin
	@GetMapping(path = "/documents/{name}/content", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody String getContentDocumentByName(@RequestHeader(name = "token", required = false) final String token,
			@PathVariable(name = "name", required = true) final String name) {

		final List<String> nestedNames = lookupNames(token);

		return nestedNames //
				.stream() //
				.map(catalogName -> serverDocumentService.getDocumentByName(catalogName, name)) //
				.findFirst() //
				.orElse(EMPTY);
	}

	private List<String> lookupNames(final String token) {

		return tokenVerificationService.lookupCatalog(token);
	}
}
