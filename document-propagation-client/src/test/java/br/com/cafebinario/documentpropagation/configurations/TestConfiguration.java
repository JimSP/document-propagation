package br.com.cafebinario.documentpropagation.configurations;

import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import com.hazelcast.core.HazelcastInstance;

import br.com.cafebinario.documentpropagation.core.DocumentCatalogResolver;
import br.com.cafebinario.documentpropagation.domains.DocumentCatalog;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages="br.com.cafebinario.documentpropagation")
public class TestConfiguration {
	
	@Bean
	public HazelcastInstance mockClientHazelcastInstance() {
		return Mockito.mock(HazelcastInstance.class);
	}
	
	@Bean("PUBLIC-CLIENT")
	@Primary
	public DocumentCatalogResolver documentCatalogResolverPublic() {
		return DocumentCatalog.PUBLIC;
	}
	
	@Bean("PRIVATE-CLIENT")
	public DocumentCatalogResolver documentCatalogResolverPrivate() {
		return DocumentCatalog.PRIVATE;
	}
	
	@Bean
	public RestTemplate clientRestTemplate() {
		return new RestTemplate();
	}
}
