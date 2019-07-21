package br.com.cafebinario.documentpropagation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;
import br.com.cafebinario.documentpropagation.services.DiscoveryService;
import br.com.cafebinario.logger.Log;

@RestController
public class DocumentInstanceDiscoveryController {

	@Autowired
	private DiscoveryService discoveryService;

	@Log
	@GetMapping(path = { "/instances" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody List<DocumentInstanceDTO> getInstances() {
		return discoveryService.getAllNodes();
	}
}
