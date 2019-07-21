package br.com.cafebinario.documentpropagation.services;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.documentpropagation.configurations.TestConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestConfiguration.class })
@ActiveProfiles("test")
public class WindowsDetectServiceTest {

	@Autowired
	private WindowsDetectService windowsDetectService;

	@Test
	public void isWindowsSystemOperational() {
		assertThat(windowsDetectService.isWindowsSystemOperational(), Is.isA(Boolean.TYPE));
	}
}
