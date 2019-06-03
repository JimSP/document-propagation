package br.com.cafebinario.documentpropagation.services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import br.com.cafebinario.documentpropagation.exceptions.CannotResolveDocumentName;

@Service
public class NetworkService {

	private static final String SEPARATOR = ":";
	private static final String PROTOCOL = "http://";

	public String getHostName() {
		
		try {

			final InetAddress localHost = InetAddress.getLocalHost();
			
			return localHost.getHostName();
		} catch (UnknownHostException ex) {
			
			throw new CannotResolveDocumentName(ex);
		}
	}

	public String getUrl(final String host, final Integer port, final String path) {
		
		return PROTOCOL + host + SEPARATOR + port + path;
	}
}
