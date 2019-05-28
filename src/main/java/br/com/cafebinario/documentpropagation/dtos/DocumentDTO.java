package br.com.cafebinario.documentpropagation.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class DocumentDTO implements Serializable{

	private static final long serialVersionUID = 2869895366094660553L;

	private final RefDocumentDTO documentIdentifier;
	private final String content;
	private final LocalDateTime lastUpdate;
}
