package br.com.cafebinario.documentpropagation.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder(toBuilder=true)
@AllArgsConstructor
@EqualsAndHashCode(of = "documentIdentifier")
public final class DocumentDTO implements Serializable {

	private static final long serialVersionUID = 2869895366094660553L;

	private final RefDocumentDTO documentIdentifier;
	private final String content;
	private final String url;
	private final LocalDateTime lastUpdate;
}
