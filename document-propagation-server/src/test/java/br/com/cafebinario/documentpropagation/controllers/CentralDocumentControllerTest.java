package br.com.cafebinario.documentpropagation.controllers;

import static br.com.cafebinario.documentpropagation.utils.TestUtils.CONTENT_DOCUMENTS_BY_NAME_PATH;
import static br.com.cafebinario.documentpropagation.utils.TestUtils.CONTENT_DOCUMENTS_PATH;
import static br.com.cafebinario.documentpropagation.utils.TestUtils.DOCUMENTS_PATH;
import static br.com.cafebinario.documentpropagation.utils.TestUtils.HEADER_TOKEN_PARAM;
import static br.com.cafebinario.documentpropagation.utils.TestUtils.NAME_1;
import static br.com.cafebinario.documentpropagation.utils.TestUtils.NAME_2;
import static br.com.cafebinario.documentpropagation.utils.TestUtils.TOKEN;
import static br.com.cafebinario.documentpropagation.utils.TestUtils.EMPTY;

import static br.com.cafebinario.documentpropagation.utils.TestUtils.getDocumentDTOs;
import static br.com.cafebinario.documentpropagation.utils.TestUtils.getFirst;
import static br.com.cafebinario.documentpropagation.utils.TestUtils.getRefDocumentsDTO;
import static br.com.cafebinario.documentpropagation.utils.TestUtils.getSecond;
import static br.com.cafebinario.documentpropagation.utils.TestUtils.toJson;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
	
import java.util.Arrays;
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

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;
import br.com.cafebinario.documentpropagation.services.ServerDocumentService;
import br.com.cafebinario.documentpropagation.services.TokenVerificationService;

@RunWith(SpringRunner.class)
@WebMvcTest(CentralDocumentController.class)
public class CentralDocumentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ServerDocumentService serverDocumentService;

	@MockBean
	private TokenVerificationService tokenVerificationService;

	@Test
	public void getRefDocuments() throws JsonProcessingException, Exception {

		final List<RefDocumentDTO> expected = getRefDocumentsDTO();

		final String token = null;

		Mockito //
				.when(tokenVerificationService.lookupCatalog(token)) //
				.thenReturn(Arrays.asList(NAME_1, NAME_2));

		Mockito //
				.when(serverDocumentService.listAllRefs(NAME_1)) //
				.thenReturn(getFirst(expected));

		Mockito //
				.when(serverDocumentService.listAllRefs(NAME_2)) //
				.thenReturn(getSecond(expected));

		mockMvc //
				.perform(get(DOCUMENTS_PATH)) //
				.andDo(print()) //
				.andExpect(content() //
						.json(toJson(expected))) //
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void getRefDocumentsWithToken() throws JsonProcessingException, Exception {

		final List<RefDocumentDTO> expected = getRefDocumentsDTO();

		Mockito //
				.when(tokenVerificationService.lookupCatalog(TOKEN)) //
				.thenReturn(Arrays.asList(NAME_1, NAME_2));

		Mockito //
				.when(serverDocumentService.listAllRefs(NAME_1)) //
				.thenReturn(getFirst(expected));

		Mockito //
				.when(serverDocumentService.listAllRefs(NAME_2)) //
				.thenReturn(getSecond(expected));

		mockMvc //
				.perform( //
						get(DOCUMENTS_PATH).header(HEADER_TOKEN_PARAM, TOKEN)) //
				.andDo(print()) //
				.andExpect(status().is2xxSuccessful()) //
				.andExpect( //
						content().json(toJson(expected)));
	}

	@Test
	public void getContentDocuments() throws JsonProcessingException, Exception {

		final List<DocumentDTO> expected = getDocumentDTOs();

		final String token = null;

		Mockito //
				.when(tokenVerificationService.lookupCatalog(token)) //
				.thenReturn(Arrays.asList(NAME_1, NAME_2));

		Mockito //
				.when(serverDocumentService.listAllDocuments(NAME_1)) //
				.thenReturn(getFirst(expected));

		Mockito //
				.when(serverDocumentService.listAllDocuments(NAME_2)) //
				.thenReturn(getSecond(expected));

		mockMvc //
				.perform(get(CONTENT_DOCUMENTS_PATH)) //
				.andDo(print()) //
				.andExpect(status().is2xxSuccessful()) //
				.andExpect(content().json(toJson(expected)));
	}

	@Test
	public void getContentDocumentByName() throws JsonProcessingException, Exception {

		final String expected = EMPTY;

		final String token = null;

		Mockito //
				.when(tokenVerificationService.lookupCatalog(token)) //
				.thenReturn(Arrays.asList(NAME_1, NAME_2));

		Mockito //
				.when(serverDocumentService.getDocumentByName(NAME_1, "documentName")) //
				.thenReturn(expected);

		mockMvc //
				.perform(get(CONTENT_DOCUMENTS_BY_NAME_PATH)) //
				.andDo(print()) //
				.andExpect(status().is2xxSuccessful()) //
				.andExpect(content().json(expected));
	}
	
	@Test
	public void getContentDocumentByNameNotFound() throws JsonProcessingException, Exception {

		final String expected = EMPTY;

		final String token = null;

		Mockito //
				.when(tokenVerificationService.lookupCatalog(token)) //
				.thenReturn(Collections.emptyList());

		mockMvc //
				.perform(get(CONTENT_DOCUMENTS_BY_NAME_PATH)) //
				.andDo(print()) //
				.andExpect(status().is2xxSuccessful()) //
				.andExpect(content().json(expected));
	}
}
