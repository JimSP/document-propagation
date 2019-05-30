package br.com.cafebinario.documentpropagation.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;
import br.com.cafebinario.documentpropagation.service.DiscoveryService;

@RestController
public class DocumentInstanceDiscoveryController {

	private DiscoveryService discoveryService;

	@GetMapping(path = { "/instances" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody List<DocumentInstanceDTO> getInstances() {
		return discoveryService.getAllNodes();
	}
}
