package br.com.cafebinario.documentpropagation.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@EnableConfigurationProperties
public class SwaggerConfiguration {

	@Value("${br.com.cafebinario.documentpropagation.swagger.title: }")
	private String title;
	
	@Value("${br.com.cafebinario.documentpropagation.swagger.description: }")
	private String description;
	
	@Value("${br.com.cafebinario.documentpropagation.swagger.contactName: }")
	private String contactName;
	
	@Value("${br.com.cafebinario.documentpropagation.swagger.contactUrl: }")
	private String contactUrl;
	
	@Value("${br.com.cafebinario.documentpropagation.swagger.contactEmail: }")
	private String contactEmail;
	
	@Value("${br.com.cafebinario.documentpropagation.swagger.license: }")
	private String license;
	
	@Value("${br.com.cafebinario.documentpropagation.swagger.licenseUrl: }")
	private String licenseUrl;
	
	@Value("${br.com.cafebinario.documentpropagation.swagger.version: }")
	private String version;
	
	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2) //
				.select() //
				.paths(PathSelectors.regex("/.*")) //
				.build() //
				.apiInfo(apiEndPointsInfo());

	}

	private ApiInfo apiEndPointsInfo() {

		return new ApiInfoBuilder() //
				.title(title) //
				.description(description) //
				.contact(createContact()) //
				.license(license) //
				.licenseUrl(licenseUrl) //
				.version(version) //
				.build();

	}

	private Contact createContact() {
		return new Contact( //
				contactName, //
				contactUrl, //
				contactEmail);
	}
}
