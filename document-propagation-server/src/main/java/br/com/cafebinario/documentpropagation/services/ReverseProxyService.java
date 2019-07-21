package br.com.cafebinario.documentpropagation.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Joiner;

import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;
import br.com.cafebinario.logger.Log;

@Service
public class ReverseProxyService {

	private static final String SCHEMA = "http";

	@Autowired
	private RestTemplate restTemplate;

	@Log
	public ResponseEntity<Object> reverseProxy( //
			final DocumentInstanceDTO documentInstance, //
			final HttpMethod httpMethod, //
			final MultiValueMap<String, String> httpHeaders, //
			final Object payload, //
			final String targetPath, //
			final Map<String, String> params) throws URISyntaxException {

		final String queryString = httpParamsToQueryString(params);

		final URI uri = createUri(targetPath, queryString, documentInstance);

		final HttpEntity<Object> httpEntity = createHttpEntity(httpHeaders, payload);

		return restTemplate.exchange(uri, httpMethod, httpEntity, Object.class);
	}
	
	private String httpParamsToQueryString(final Map<String, String> params) {
		
		return Joiner //
				.on("&") //
				.withKeyValueSeparator("=") //
				.join(params);
	}

	private URI createUri(final String requestUri, final String queryString,
			final DocumentInstanceDTO documentInstance) throws URISyntaxException {

		if(StringUtils.isAllBlank(queryString)) {
			return new URI(SCHEMA, null, documentInstance.getHostName(), documentInstance.getPort(), requestUri,
					null, null);
		}
		
		return new URI(SCHEMA, null, documentInstance.getHostName(), documentInstance.getPort(), requestUri,
				queryString, null);
	}

	private HttpEntity<Object> createHttpEntity(final MultiValueMap<String, String> httpHeaders,
			final Object body) throws URISyntaxException {

		return new HttpEntity<>(body, httpHeaders);
	}
}
