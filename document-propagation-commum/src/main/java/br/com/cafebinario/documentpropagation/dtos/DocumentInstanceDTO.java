package br.com.cafebinario.documentpropagation.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DocumentInstanceDTO implements Serializable{

	private static final long serialVersionUID = -6970183277041024220L;

	private final String address;
	private final Integer hazelcastPort;
}
