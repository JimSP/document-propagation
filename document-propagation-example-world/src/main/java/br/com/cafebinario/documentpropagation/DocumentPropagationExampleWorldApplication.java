package br.com.cafebinario.documentpropagation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.documentpropagation.annotations.EnableDocumentPropagation;
import br.com.cafebinario.documentpropagation.core.DocumentCatalogResolver;
import br.com.cafebinario.documentpropagation.domains.DocumentCatalog;

@SpringBootApplication
@EnableDocumentPropagation
@RestController
public class DocumentPropagationExampleWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentPropagationExampleWorldApplication.class, args);
	}

	@GetMapping(path="/world", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody String value() {
		return "world";
	}
	
	@Bean
	@Primary
	public DocumentCatalogResolver myDocumentCatalogResolver() {
		return DocumentCatalog.PRIVATE;
	}
}
