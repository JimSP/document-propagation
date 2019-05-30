package br.com.cafebinario.documentpropagation.configurations;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import br.com.cafebinario.documentpropagation.annotations.EnableDocumentPropagation;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;
import br.com.cafebinario.documentpropagation.service.NetworkService;
import br.com.cafebinario.documentpropagation.service.ServerDocumentService;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Configuration
@EnableDocumentPropagation
public class ServerDocumentPropagationConfiguration {

	private static final String SWAGGER_VERSION = "2.0";

	@Value("${server.port:8080}")
	private Integer serverPort;

	@Bean
	@Primary
	@Lazy
	public SwaggerResourcesProvider swaggerResourcesProvider(
			@Autowired final ServerDocumentService serverDocumentService,
			@Autowired final NetworkService networkService) {

		return () -> serverDocumentService //
				.listAllRefs() //
				.stream() //
				.map(refDocument -> createSwaggerResource(refDocument, networkService)) //
				.collect(Collectors.toList());
	}

	private SwaggerResource createSwaggerResource(final RefDocumentDTO refDocumentDTO,
			final NetworkService networkService) {

		final SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setUrl(formmatLocalUrl(refDocumentDTO, networkService));
		swaggerResource.setName(refDocumentDTO.getName());
		swaggerResource.setSwaggerVersion(SWAGGER_VERSION);
		return swaggerResource;
	}

	private String formmatLocalUrl(final RefDocumentDTO refDocumentDTO, final NetworkService networkService) {

		final String hostName = networkService.getHostName();
		final String documentName = refDocumentDTO.getName();
		final String path = getPath(documentName);

		return networkService.getUrl(hostName, serverPort, path);
	}

	private String getPath(final String documentName) {

		return "/documents/" + documentName + "/content";
	}
}
