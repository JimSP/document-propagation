package br.com.cafebinario.documentpropagation.exceptions;

public class CannotResolveDocumentName extends RuntimeException{

	private static final long serialVersionUID = -1348333057802376461L;

	public CannotResolveDocumentName(final Exception ex) {
		super(ex);
	}

	public CannotResolveDocumentName() {
		super();
	}
}
