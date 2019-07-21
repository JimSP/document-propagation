package br.com.cafebinario.documentpropagation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.documentpropagation.annotations.EnableDocumentPropagation;
import br.com.cafebinario.logger.Log;
import br.com.cafebinario.logger.VerboseMode;

@SpringBootApplication
@EnableDocumentPropagation
@RestController
public class DocumentPropagationExampleHelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentPropagationExampleHelloApplication.class, args);
	}
	
	@Log(verboseMode=VerboseMode.ON)
	@GetMapping(path="/hello", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody String value() {
		return "hello";
	}
}
