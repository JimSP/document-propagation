package br.com.cafebinario.documentpropagation.domains;

import java.util.Arrays;
import java.util.List;

import br.com.cafebinario.documentpropagation.core.DocumentCatalogResolver;

public enum DocumentCatalog implements DocumentCatalogResolver {

	PUBLIC {
		@Override
		public String getCatalogName() {
			return "documentCatalogPublic";
		}

		@Override
		public List<String> getNestedNames(String catalogName) {
			return Arrays.asList(getCatalogName());
		}
	},
	PRIVATE {
		@Override
		public String getCatalogName() {
			return "documentCatalogPrivate";
		}

		@Override
		public List<String> getNestedNames(final String catalogName) {
			return Arrays.asList(getCatalogName(), PUBLIC.getCatalogName());
		}
	};
	
	public static Boolean isPublic(final DocumentCatalogResolver documentCatalogResolver) {
		return PUBLIC.getCatalogName().equalsIgnoreCase(documentCatalogResolver.getCatalogName());
	}
	
	public static Boolean isPrivate(final DocumentCatalogResolver documentCatalogResolver) {
		return PRIVATE.getCatalogName().equalsIgnoreCase(documentCatalogResolver.getCatalogName());
	}
}
