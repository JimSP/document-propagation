package br.com.cafebinario.documentpropagation.services;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TokenVerificationService.class)
@ActiveProfiles("test")
public class TokenVerificationServiceTest {

	private static final String PUBLIC = "documentCatalogPublic";
	private static final String TOKEN = "token";
	private static final String EMPTY = "";

	@Autowired
	private TokenVerificationService tokenVerificationService;

	@MockBean
	private Map<String, String> tokens;

	@Test
	public void lookupCatalog() {

		Mockito //
				.when(tokens.containsKey(TOKEN)) //
				.thenReturn(Boolean.TRUE);

		Mockito //
				.when(tokens.get(TOKEN)) //
				.thenReturn(EMPTY);

		final List<String> names = tokenVerificationService.lookupCatalog(TOKEN);
		assertEquals(PUBLIC, names.get(0));
	}
}
