package br.com.cafebinario.documentpropagation.domain;

public enum DocumentCatalog implements DocumentCatalogResolver{

	DEFAULT {
		@Override
		public String getCatalogName() {
			return "documentCatalog";
		}
	};
}
