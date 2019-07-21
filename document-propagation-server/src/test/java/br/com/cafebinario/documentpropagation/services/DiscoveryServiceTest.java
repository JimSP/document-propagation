package br.com.cafebinario.documentpropagation.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hazelcast.core.Cluster;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import com.hazelcast.nio.Address;

import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiscoveryService.class)
@ActiveProfiles("test")
public class DiscoveryServiceTest {

	@Autowired
	private DiscoveryService discoveryService;

	@MockBean
	@Qualifier("mockClientHazelcastInstance")
	private HazelcastInstance mockClientHazelcastInstance;

	@MockBean
	private Cluster cluster;

	@MockBean
	private Set<Member> members;

	@MockBean
	private Member member;

	@Test
	public void getAllNodes() throws UnknownHostException {

		final Address address = new Address("localhost", 9999);

		Mockito.when(mockClientHazelcastInstance.getCluster()).thenReturn(cluster);

		Mockito.when(cluster.getMembers()).thenReturn(members);

		Mockito.when(members.stream()).thenReturn(StreamSupport.stream(Arrays.asList(member).spliterator(), false));

		Mockito.when(member.getAddress()).thenReturn(address);

		final List<DocumentInstanceDTO> documentInstances = discoveryService.getAllNodes();

		assertThat(documentInstances).contains(DocumentInstanceDTO.builder().hostName("localhost").port(9999).build());
	}
}
