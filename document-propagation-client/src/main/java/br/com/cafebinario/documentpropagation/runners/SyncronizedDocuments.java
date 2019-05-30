package br.com.cafebinario.documentpropagation.runners;

import org.springframework.beans.factory.DisposableBean;

import br.com.cafebinario.documentpropagation.dtos.DocumentDTO;

public interface SyncronizedDocuments extends DisposableBean {

	DocumentDTO getLocalDocument();
}
