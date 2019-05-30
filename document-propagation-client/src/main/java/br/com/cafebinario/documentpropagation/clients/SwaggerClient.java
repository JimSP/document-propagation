package br.com.cafebinario.documentpropagation.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(url = "http://localhost:${server.port}", value = "swaggerClient")
public interface SwaggerClient {

	@GetMapping(path = "/{swaggerApi}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody String getDocument(
			@PathVariable(name = "swaggerApi", required = true) final String swaggerApi);
}
