package br.com.cafebinario.documentpropagation.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import com.hazelcast.nio.Address;

import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;

@Service
public class DiscoveryService {

	@Autowired
	private HazelcastInstance hazelcastInstance;

	public List<DocumentInstanceDTO> getAllNodes() {
		return hazelcastInstance //
				.getCluster() //
				.getMembers() //
				.stream() //
				.map(Member::getAddress) //
				.map(this::createDocumentInstance) //
				.collect(Collectors.toList());
	}

	private DocumentInstanceDTO createDocumentInstance(final Address address) {
		return DocumentInstanceDTO //
				.builder() //
				.address(address.getHost()) //
				.hazelcastPort(address.getPort()) //
				.build();
	}
}
