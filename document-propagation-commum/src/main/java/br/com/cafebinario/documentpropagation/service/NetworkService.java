package br.com.cafebinario.documentpropagation.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import br.com.cafebinario.documentpropagation.exceptions.CannotResolveDocumentName;

@Service
public class NetworkService {

	public String getHostName() {
		try {
			return InetAddress //
					.getLocalHost() //
					.getHostName();
		} catch (UnknownHostException ex) {
			throw new CannotResolveDocumentName(ex);
		}
	}

	public String getUrl(final String host, final Integer port, final String path) {
		return "http://" + host + ":" + port + "/" + path;
	}
}
