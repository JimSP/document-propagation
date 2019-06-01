package br.com.cafebinario.documentpropagation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import br.com.cafebinario.documentpropagation.configurations.DocumentCatalogAutoConfiguration;
import br.com.cafebinario.documentpropagation.configurations.HazelcastAutoConfiguration;
import br.com.cafebinario.documentpropagation.configurations.SwaggerAutoConfiguration;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(value = { //
		SwaggerAutoConfiguration.class, //
		DocumentCatalogAutoConfiguration.class, //
		HazelcastAutoConfiguration.class})
public @interface EnableDocumentPropagation {

}
