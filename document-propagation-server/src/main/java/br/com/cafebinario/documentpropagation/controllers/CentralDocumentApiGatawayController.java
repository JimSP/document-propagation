package br.com.cafebinario.documentpropagation.controllers;

import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;
import br.com.cafebinario.documentpropagation.services.LoadBalanceService;
import br.com.cafebinario.documentpropagation.services.ReverseProxyService;
import br.com.cafebinario.logger.Log;
import br.com.cafebinario.logger.LogLevel;
import br.com.cafebinario.logger.VerboseMode;

@RestController
public class CentralDocumentApiGatawayController {

	@Autowired
	private ReverseProxyService reverseProxyService;

	@Autowired
	private LoadBalanceService loadBalanceService;

	@Log
	@CrossOrigin
	@RequestMapping(path = "/document-gataway/{applicationName}/{targetPath}", //
			method = { RequestMethod.DELETE, //
					RequestMethod.GET, //
					RequestMethod.HEAD, //
					RequestMethod.OPTIONS, //
					RequestMethod.PATCH, //
					RequestMethod.POST, //
					RequestMethod.PUT, //
					RequestMethod.TRACE }, //
			consumes = MediaType.ALL_VALUE, //
			produces = MediaType.ALL_VALUE)
	public ResponseEntity<Object> reverseProxy(
			final HttpMethod httpMethod,
			@RequestHeader(required = false) final MultiValueMap<String, String> httpHeaders, //
			@RequestBody(required=false) final Object payload,
			@PathVariable(required = true) final String applicationName, //
			@PathVariable(required = false) final String targetPath, //
			@RequestParam(required = false) final Map<String, String> params) throws URISyntaxException {

		final DocumentInstanceDTO documentInstance = loadBalanceService.chooseElegibleInstance(applicationName);

		return reverseProxyService.reverseProxy(documentInstance, httpMethod, httpHeaders, payload, targetPath, params);
	}
}
