package br.com.cafebinario.documentpropagation.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;
import br.com.cafebinario.documentpropagation.services.LoadBalanceService;

@RestController
public class CentralDocumentApiGatawayController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private LoadBalanceService loadBalanceService;

	@CrossOrigin
	@RequestMapping(path = "/document-gataway/{applicationName}/**", //
			method = { RequestMethod.DELETE, //
					RequestMethod.GET, //
					RequestMethod.HEAD, //
					RequestMethod.OPTIONS, //
					RequestMethod.PATCH, //
					RequestMethod.POST, //
					RequestMethod.PUT, //
					RequestMethod.TRACE }, //
			consumes = { MediaType.ALL_VALUE })
	public ResponseEntity<byte[]> reverseProxy( //
			final HttpMethod httpMethod, //
			final HttpHeaders httpHeaders,
			@RequestBody(required = false) final byte[] body, //
			@PathVariable(required = true) final String applicationName, //
			final HttpServletRequest request) //
			throws URISyntaxException {
		
		final DocumentInstanceDTO documentInstance = loadBalanceService.chooseElegibleInstance(applicationName);
		
		final URI uri = createUri(request, documentInstance);
				
		final HttpEntity<byte[]> httpEntity = createHttpEntity(httpHeaders, body);

		return restTemplate.exchange(uri, httpMethod, httpEntity, byte[].class);
	}

	private URI createUri(final HttpServletRequest request, final DocumentInstanceDTO documentInstance) throws URISyntaxException {
		return new URI("http", null, documentInstance.getHostName(), documentInstance.getPort(), request.getRequestURI(), request.getQueryString(), null);
	}
	
	private HttpEntity<byte[]> createHttpEntity(final HttpHeaders httpHeaders, final byte[] body) throws URISyntaxException {
		return new HttpEntity<>(body, httpHeaders);
	}
}
