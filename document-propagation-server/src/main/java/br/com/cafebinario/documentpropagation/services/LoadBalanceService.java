package br.com.cafebinario.documentpropagation.services;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import br.com.cafebinario.documentpropagation.dtos.DocumentInstanceDTO;
import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;
import br.com.cafebinario.documentpropagation.exceptions.CannotResolveDocumentName;

@Service
public class LoadBalanceService {

	@Autowired
	private HazelcastInstance hazelcastInstance;

	@Autowired
	private ServerDocumentService serverDocumentService;

	public DocumentInstanceDTO chooseElegibleInstance(final String applicationName) {

		final List<RefDocumentDTO> documentIdentifiers = listIdentifiers(applicationName);

		final RefDocumentDTO elegible = choose(documentIdentifiers);

		final String[] details = extractInstanceDetails(elegible.getName());

		return createDocumentInstance(applicationName, details);
	}

	private List<RefDocumentDTO> listIdentifiers(final String applicationName) {
		return serverDocumentService //
				.listAllRefs() //
				.stream() //
				.filter(filterByApplicationName(applicationName)) //
				.collect(Collectors.toList());
	}

	private RefDocumentDTO choose(final List<RefDocumentDTO> documentIdentifiers) throws CannotResolveDocumentName {

		final IMap<RefDocumentDTO, BigInteger> balanceMap = hazelcastInstance.getMap("balanceMap");

		BigInteger antBalance = BigInteger.valueOf(Long.MAX_VALUE);
		RefDocumentDTO antDocumentIdentifier = null;
		for (RefDocumentDTO documentIdentifier : documentIdentifiers) {
			BigInteger balance = balanceMap.get(documentIdentifier);
			if (balance != null && balance.compareTo(antBalance) <= 0) {
				antBalance = balance;
				antDocumentIdentifier = documentIdentifier;
			}
		}

		if (antDocumentIdentifier == null) {
			throw new CannotResolveDocumentName();
		}

		balanceMap.set(antDocumentIdentifier, antBalance.add(BigInteger.ONE));

		return antDocumentIdentifier;
	}

	private Predicate<? super RefDocumentDTO> filterByApplicationName(final String applicationName) {
		return documentIdetifier -> extractInstanceDetails(documentIdetifier.getName())[0]
				.equalsIgnoreCase(applicationName);
	}

	private String[] extractInstanceDetails(final String name) {
		return StringUtils.split(name, "-");
	}

	private DocumentInstanceDTO createDocumentInstance(final String applicationName, final String[] details) {
		return DocumentInstanceDTO //
				.builder() //
				.applicationName(applicationName) //
				.hostName(details[1]) //
				.port(Integer.parseInt(details[2])) //
				.build();
	}
}
