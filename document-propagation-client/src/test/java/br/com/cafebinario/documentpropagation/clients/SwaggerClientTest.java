package br.com.cafebinario.documentpropagation.clients;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import br.com.cafebinario.documentpropagation.configurations.TestConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestConfiguration.class })
@ActiveProfiles("test")
public class SwaggerClientTest {

	private static final String CONTENT = "content";
	private static final String PATH = "path";
	
	@Autowired
	private SwaggerClient swaggerClient;
	
	@MockBean
	private RestTemplate restTemplate;
	
	@Test
	public void test() {
		final String url = swaggerClient.getUrl(PATH);
		Mockito//
			.when(restTemplate.getForObject(url, String.class))
			.thenReturn(CONTENT);
			
		
		final String document = swaggerClient.getDocument(PATH);
		
		assertEquals(CONTENT, document);
	}
}
