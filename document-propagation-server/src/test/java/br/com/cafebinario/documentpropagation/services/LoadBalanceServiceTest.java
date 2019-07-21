package br.com.cafebinario.documentpropagation.services;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoadBalanceService.class)
@ActiveProfiles("test")
public class LoadBalanceServiceTest {

	private static final String APPLICATION_NAME = "teste";
	private static final String NAME_INSTANCE_A = "teste-hostA-8080";
	private static final String NAME_INSTANCE_B = "teste-hostB-8080";
	private static final String NAME_INSTANCE_C = "teste-hostC-8080";

	@Autowired
	private LoadBalanceService loadBalanceService;

	@MockBean(name = "hazelcastInstance")
	private HazelcastInstance hazelcastInstance;

	@MockBean
	private IMap<Object, Object> balanceMap;

	@MockBean
	private ServerDocumentService serverDocumentService;

	@Test
	public void chooseElegibleInstance() {

		final RefDocumentDTO instanceA = RefDocumentDTO //
				.builder() //
				.name(NAME_INSTANCE_A) //
				.build();

		final RefDocumentDTO instanceB = RefDocumentDTO //
				.builder() //
				.name(NAME_INSTANCE_B) //
				.build();

		final RefDocumentDTO instanceC = RefDocumentDTO //
				.builder() //
				.name(NAME_INSTANCE_C) //
				.build();

		Mockito //
				.when(serverDocumentService.listAllRefs()) //
				.thenReturn(Arrays.asList(instanceA, instanceB, instanceC));

		Mockito //
				.when(hazelcastInstance.getMap("balanceMap")) //
				.thenReturn(balanceMap);

		Mockito //
				.when(balanceMap.get(instanceA)) //
				.thenReturn(BigInteger.ONE);

		Mockito //
				.when(balanceMap.get(instanceB)) //
				.thenReturn(BigInteger.TEN);

		Mockito //
				.when(balanceMap.get(instanceC)) //
				.thenReturn(BigInteger.ONE);

		loadBalanceService.chooseElegibleInstance(APPLICATION_NAME);
		loadBalanceService.chooseElegibleInstance(APPLICATION_NAME);
		final DocumentInstanceDTO documentInstance3 = loadBalanceService.chooseElegibleInstance(APPLICATION_NAME);

		final DocumentInstanceDTO expected = DocumentInstanceDTO.builder().applicationName(APPLICATION_NAME)
				.hostName("hostC").port(8080).build();

		assertEquals(expected, documentInstance3);

	}
}
