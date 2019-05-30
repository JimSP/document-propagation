package br.com.cafebinario.documentpropagation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.documentpropagation.annotations.EnableDocumentPropagation;

@SpringBootApplication
@EnableDocumentPropagation
@RestController
public class DocumentPropagationExampleHelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentPropagationExampleHelloApplication.class, args);
	}
	
	@GetMapping(path="/hello", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody String value() {
		return "hello";
	}

}
