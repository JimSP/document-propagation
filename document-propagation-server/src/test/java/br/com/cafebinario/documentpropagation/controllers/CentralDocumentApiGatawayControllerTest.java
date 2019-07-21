package br.com.cafebinario.documentpropagation.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;
import br.com.cafebinario.documentpropagation.services.LoadBalanceService;
import br.com.cafebinario.documentpropagation.services.ReverseProxyService;

@RunWith(SpringRunner.class)
@WebMvcTest(CentralDocumentApiGatawayController.class)
public class CentralDocumentApiGatawayControllerTest {

	private static final int PORT = 5410;
	private static final String HOST_NAME = "hostName";
	private static final String APPLICATION_NAME = "applicationName";
	private static final URI GET_PATH = URI.create("/document-gataway/applicationName/anything?varA=1&varB=dog");
	private static final Object EXPECTED = Collections.singletonList(1);

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReverseProxyService reverseProxyService;

	@MockBean
	private LoadBalanceService loadBalanceService;

	@Test
	public void reverseProxyWithGetHeaderAndTargetPathAndQueryParamAndHttpStatusOK() throws Exception {

		final DocumentInstanceDTO documentInstance = DocumentInstanceDTO //
				.builder() //
				.applicationName(APPLICATION_NAME) //
				.hostName(HOST_NAME) //
				.port(PORT) //
				.build();

		Mockito //
				.when(loadBalanceService.chooseElegibleInstance(APPLICATION_NAME)) //
				.thenReturn(documentInstance);

		final Map<String, String> params = new LinkedHashMap<>();
		params.put("varA", "1");
		params.put("varB", "dog");

		final LinkedMultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<>();
		httpHeaders.add("keyA", "value");
		httpHeaders.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);

		final ResponseEntity<Object> responseEntity = ResponseEntity.ok(EXPECTED);

		Mockito.when(reverseProxyService.reverseProxy(documentInstance, HttpMethod.GET, httpHeaders, null,
				"anything", params)).thenReturn(responseEntity);
		
		final String responseData = new ObjectMapper().writeValueAsString(EXPECTED);
		
		mockMvc //
				.perform(get(GET_PATH)
						.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.header("keyA", "value"))
				.andDo(print()) //
				.andExpect(content().json(responseData)) //
				.andExpect(status().is2xxSuccessful());
	}
}
