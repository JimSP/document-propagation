package br.com.cafebinario.documentpropagation.utils;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;

public final class TestUtils {
	
	public static final String NAME_1 = "name1";
	public static final String NAME_2 = "name2";

	private static final int BEGIN_FIRST = 0;
	private static final int END_FIRST = 1;
	private static final int END_SECOUND = 2;

	public static final String HEADER_TOKEN_PARAM = "token";
	public static final URI DOCUMENTS_PATH = URI.create("/documents");
	public static final URI CONTENT_DOCUMENTS_PATH = URI.create("/documents/contents");
	public static final URI CONTENT_DOCUMENTS_BY_NAME_PATH = URI.create("/documents/documentName/content");
	public static final URI INSTANCES_PATH = URI.create("/instances");
	public static final String TOKEN = "TOKEN";
	
	public static final String EMPTY = "{}";
	
	private TestUtils() {
		
	}
	
	public static List<RefDocumentDTO> getRefDocumentsDTO() {

		return Arrays.asList( //
				documentIdentifier(NAME_1), //
				documentIdentifier(NAME_2));
	}

	public static <T> List<T> getFirst(final List<T> expected) {

		return expected.subList(BEGIN_FIRST, END_FIRST);
	}

	public static <T> List<T> getSecond(final List<T> expected) {

		return expected.subList(END_FIRST, END_SECOUND);
	}

	public static List<DocumentDTO> getDocumentDTOs() {

		return Arrays.asList(getDocumentDTO(documentIdentifier(NAME_1)), getDocumentDTO(documentIdentifier(NAME_2)));
	}

	public static DocumentDTO getDocumentDTO(final RefDocumentDTO documentIdentifier) {

		return DocumentDTO //
				.builder() //
				.documentIdentifier(documentIdentifier) //
				.build();
	}

	public static RefDocumentDTO documentIdentifier(final String name) {

		return RefDocumentDTO //
				.builder() //
				.name(name) //
				.build();
	}

	public static <T> String toJson(final T value) throws JsonProcessingException {

		return new ObjectMapper().writeValueAsString(value);
	}
	
}
