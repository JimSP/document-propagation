package br.com.cafebinario.documentpropagation.dtos;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class RefDocumentDTO implements DocumentKey {

	private static final long serialVersionUID = -5936359727736383933L;

	@NotBlank
	private final String name;

	@Override
	public int compareTo(final DocumentKey o) {
		return (name != null ? name.hashCode() : 0);
	}
}
