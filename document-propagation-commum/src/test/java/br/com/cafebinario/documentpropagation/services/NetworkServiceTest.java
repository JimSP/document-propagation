package br.com.cafebinario.documentpropagation.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.hamcrest.text.IsEmptyString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.documentpropagation.configurations.TestConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestConfiguration.class })
public class NetworkServiceTest {

	@Autowired
	private NetworkService networkService;

	@Test
	public void getUrl() {
		final String url = networkService.getUrl("teste", 5555, "/testando");
		assertEquals("http://teste:5555/testando", url);
	}
	
	@Test
	public void getHostName() {
		final String hostName = networkService.getHostName();
		assertThat(hostName, CoreMatchers.not(IsEmptyString.isEmptyOrNullString()));
	}
}
