package br.com.cafebinario.documentpropagation.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			@RequestBody(required = false) final byte[] body, //
			@PathVariable(required = true) final String applicationName, final HttpServletRequest request, //
			final HttpServletResponse response) //
			throws URISyntaxException {
		
		final String[] instanceDetails = loadBalanceService.chooseElegibleInstance(applicationName);
		
		final URI uri = createUri(request, instanceDetails);
		
		final HttpHeaders httpHeaders = header(request);
		
		final HttpEntity<byte[]> httpEntity = createHttpEntity(httpHeaders, request, body, instanceDetails);

		return restTemplate.exchange(uri, httpMethod, httpEntity, byte[].class);
	}

	private URI createUri(final HttpServletRequest request, final String[] instanceDetails) throws URISyntaxException {
		return new URI("http", null, instanceDetails[1], Integer.parseInt(instanceDetails[2]), request.getRequestURI(), request.getQueryString(), null);
	}

	private HttpHeaders header(final HttpServletRequest request) {
		final HttpHeaders headers = new HttpHeaders();
		final Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			final String headerName = headerNames.nextElement();
			headers.set(headerName, request.getHeader(headerName));
		}

		return headers;
	}
	
	private HttpEntity<byte[]> createHttpEntity(final HttpHeaders httpHeaders, final HttpServletRequest request, final byte[] body, final String[] instanceDetails) throws URISyntaxException {
		return new HttpEntity<>(body, httpHeaders);
	}
}
