package br.com.cafebinario.documentpropagation.domains;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.documentpropagation.configurations.TestConfiguration;
import br.com.cafebinario.documentpropagation.core.DocumentCatalogResolver;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestConfiguration.class })
@ActiveProfiles("test")
public class DocumentCatalogTest {

	
	@Autowired
	@Qualifier("PUBLIC-CLIENT")
	private DocumentCatalogResolver documentCatalogResolverPublic;
	
	@Autowired
	@Qualifier("PRIVATE-CLIENT")
	private DocumentCatalogResolver documentCatalogResolverPrivate;
	
	@Test
	public void publicDocumentCatalog() {
		final String catalogName =  documentCatalogResolverPublic.getCatalogName();
		assertEquals(DocumentCatalog.PUBLIC.getCatalogName(), catalogName);
	}
	
	@Test
	public void publicNestedDocumentCatalog() {
		final List<String> name =  documentCatalogResolverPublic.getNestedNames(DocumentCatalog.PUBLIC.getCatalogName());
		assertThat(name, IsIterableContainingInOrder.contains(DocumentCatalog.PUBLIC.getCatalogName()));
	}
	
	@Test
	public void privateDocumentCatalog() {
		final String catalogName =  documentCatalogResolverPrivate.getCatalogName();
		assertEquals(DocumentCatalog.PRIVATE.getCatalogName(), catalogName);
	}
	
	@Test
	public void privateNestedDocumentCatalog() {
		final List<String> name =  documentCatalogResolverPrivate.getNestedNames(DocumentCatalog.PRIVATE.getCatalogName());
		assertThat(name, IsIterableContainingInOrder.contains(DocumentCatalog.PRIVATE.getCatalogName(), DocumentCatalog.PUBLIC.getCatalogName()));
	}
}
