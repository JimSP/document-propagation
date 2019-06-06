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

import br.com.cafebinario.documentpropagation.dtos.RefDocumentDTO;
import br.com.cafebinario.documentpropagation.exceptions.CannotResolveDocumentName;

@Service
public class LoadBalanceService {

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	@Autowired
	private ServerDocumentService serverDocumentService;

	public String[] chooseElegibleInstance(final String applicationName) {
		final List<RefDocumentDTO> documentIdentifiers = serverDocumentService
				.listAllRefs()
				.stream()
				.filter(filterByApplicationName(applicationName))
				.collect(Collectors.toList());

		final RefDocumentDTO elegible = choose(documentIdentifiers);
		return extractInstanceDetails(elegible.getName());
	}
	
	private RefDocumentDTO choose(final List<RefDocumentDTO> documentIdentifiers) throws CannotResolveDocumentName {

		final IMap<RefDocumentDTO, BigInteger> balanceMap = hazelcastInstance.getMap("balanceMap");
		
		BigInteger antBalance = null;
		RefDocumentDTO antDocumentIdentifier = null;
		for (RefDocumentDTO documentIdentifier : documentIdentifiers) {
			BigInteger balance = balanceMap.get(documentIdentifier);
			if(balance != null && balance.compareTo(antBalance) <= 0) {
				antBalance = balance;
				antDocumentIdentifier = documentIdentifier;				
			}
		}
		
		if(antDocumentIdentifier == null) {
			throw new CannotResolveDocumentName();
		}
		
		balanceMap.set(antDocumentIdentifier, antBalance.add(BigInteger.ONE));
		
		return antDocumentIdentifier;
	}
	
	private Predicate<? super RefDocumentDTO> filterByApplicationName(final String applicationName) {
		return documentIdetifier->extractInstanceDetails(documentIdetifier.getName())[0].equalsIgnoreCase(applicationName);
	}
	
	private String[] extractInstanceDetails(final String name) {
		return StringUtils.split(name, "-");
	}
}
