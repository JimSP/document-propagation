package br.com.cafebinario.documentpropagation.controllers;

import static br.com.cafebinario.documentpropagation.utils.TestUtils.INSTANCES_PATH;
import static br.com.cafebinario.documentpropagation.utils.TestUtils.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;
import br.com.cafebinario.documentpropagation.services.DiscoveryService;

@RunWith(SpringRunner.class)
@WebMvcTest(DocumentInstanceDiscoveryController.class)
public class DocumentInstanceDiscoveryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DiscoveryService discoveryService;

	@Test
	public void getInstances() throws JsonProcessingException, Exception {

		final List<DocumentInstanceDTO> expected = Collections.emptyList();

		Mockito //
				.when(discoveryService.getAllNodes()) //
				.thenReturn(expected);

		mockMvc //
				.perform(get(INSTANCES_PATH)) //
				.andDo(print()) //
				.andExpect(content() //
						.json(toJson(expected))) //
				.andExpect(status().is2xxSuccessful());
	}
}
