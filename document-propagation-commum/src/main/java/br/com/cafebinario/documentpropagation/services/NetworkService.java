package br.com.cafebinario.documentpropagation.services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import br.com.cafebinario.documentpropagation.exceptions.CannotResolveDocumentName;
import br.com.cafebinario.logger.Log;
import br.com.cafebinario.logger.VerboseMode;

@Service
public class NetworkService {

	private static final String SEPARATOR = ":";
	private static final String PROTOCOL = "http://";

	@Log(verboseMode=VerboseMode.ON)
	public String getHostName() {
		
		try {

			final InetAddress localHost = InetAddress.getLocalHost();
			
			return localHost.getHostName();
		} catch (UnknownHostException ex) {
			
			throw new CannotResolveDocumentName(ex);
		}
	}

	@Log(verboseMode=VerboseMode.ON)
	public String getUrl(final String host, final Integer port, final String path) {
		
		return PROTOCOL + host + SEPARATOR + port + path;
	}
}
