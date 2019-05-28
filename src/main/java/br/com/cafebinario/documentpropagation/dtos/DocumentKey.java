package br.com.cafebinario.documentpropagation.dtos;

import java.io.Serializable;

public interface DocumentKey extends Serializable, Comparable<DocumentKey>{
	String getName();
	String getUrl();
}
