package br.com.cafebinario.documentpropagation.services;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ReverseProxyService.class)
@ActiveProfiles("test")
public class ReverseProxyServiceTest {

	@Autowired
	private ReverseProxyService reverseProxyService;
	
	@MockBean
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;
	
	@Test
	public void test() throws URISyntaxException {
		
		final DocumentInstanceDTO documentInstance = DocumentInstanceDTO.builder().hostName("hostName").port(5414).build();
		final HttpMethod httpMethod = HttpMethod.OPTIONS;
		final MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<>();
		final Object payload = new Object();
		final String targetPath = "/";
		final Map<String, String> params = new LinkedHashMap<>();
		
		final ResponseEntity<Object> expected = ResponseEntity.accepted().build();
		
		final HttpEntity<Object> httpEntity = new HttpEntity<>(payload, httpHeaders);
		
		Mockito
			.when(restTemplate.exchange(new URI("http", null, "hostName", 5414, targetPath,
					null, null), httpMethod, httpEntity, Object.class))
			.thenReturn(expected);
		
		final ResponseEntity<Object> result = reverseProxyService.reverseProxy(documentInstance, httpMethod, httpHeaders, payload, targetPath, params);
		
		assertEquals(expected, result);
	}
}
