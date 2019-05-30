package br.com.cafebinario.documentpropagation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import br.com.cafebinario.documentpropagation.configurations.DocumentCatalogConfiguration;
import br.com.cafebinario.documentpropagation.configurations.FeignConfiguration;
import br.com.cafebinario.documentpropagation.configurations.HazelcastConfiguration;
import br.com.cafebinario.documentpropagation.configurations.SwaggerConfiguration;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(value = { //
		SwaggerConfiguration.class, //
		FeignConfiguration.class, //
		DocumentCatalogConfiguration.class, //
		HazelcastConfiguration.class})
public @interface EnableDocumentPropagation {

}
