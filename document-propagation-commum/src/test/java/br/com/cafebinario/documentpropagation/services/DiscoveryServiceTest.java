package br.com.cafebinario.documentpropagation.services;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.hazelcast.core.Cluster;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;

import br.com.cafebinario.documentpropagation.configurations.TestConfiguration;
import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestConfiguration.class })
public class DiscoveryServiceTest {

	@Autowired
	private DiscoveryService discoveryService;

	@MockBean
	private HazelcastInstance hazelcastInstance;

	@MockBean
	private Cluster cluster;

	@MockBean
	private Set<Member> members;

	@Test
	public void getAllNodes() {

		Mockito.when(hazelcastInstance.getCluster()).thenReturn(cluster);

		Mockito.when(cluster.getMembers()).thenReturn(members);

		final List<DocumentInstanceDTO> documentInstances = discoveryService.getAllNodes();

		assertNotNull(documentInstances);
	}
}
