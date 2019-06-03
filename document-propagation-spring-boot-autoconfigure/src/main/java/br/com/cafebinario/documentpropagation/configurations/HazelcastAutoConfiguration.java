package br.com.cafebinario.documentpropagation.configurations;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import br.com.cafebinario.documentpropagation.core.DocumentCatalogResolver;
import br.com.cafebinario.documentpropagation.services.WindowsDetectService;

@Configuration
public class HazelcastAutoConfiguration {

	private static final String HAZELCAST_INSTANCE_NAME = "DocumentPropagation";

	@Value("${br.com.cafebinario.documentpropagation.cluster.members}")
	private String members;

	@Bean
	public Config config(@Autowired final DocumentCatalogResolver documentCatalogResolver,
			@Autowired final WindowsDetectService windowsDetectService) {

		final String catalogName = documentCatalogResolver.getCatalogName();
		
		Assert.hasText(catalogName, "catalogName must not be empty. The return value of DocumentCatalogResolver.getCatalogName() must be a valid text.");
		
		return new Config(HAZELCAST_INSTANCE_NAME) //
				.setNetworkConfig(networkConfig(windowsDetectService)) //
				.addMapConfig(mapConfig(documentCatalogResolver));
	}

	@Bean(destroyMethod = "shutdown")
	public HazelcastInstance hazelcastInstance(@Autowired final Config config) {
		return Hazelcast.getOrCreateHazelcastInstance(config);
	}

	private NetworkConfig networkConfig(final WindowsDetectService windowsDetectService) {
		return new NetworkConfig() //
				.setJoin(join(windowsDetectService));
	}

	private JoinConfig join(final WindowsDetectService windowsDetectService) {

		if (windowsDetectService.isWindowsSystemOperational()) {
			return new JoinConfig() //
					.setTcpIpConfig(tcpIpConfig()) //
					.setMulticastConfig(multicastConfig() //
							.setEnabled(false));
		}

		return new JoinConfig() //
				.setMulticastConfig(multicastConfig());
	}

	private TcpIpConfig tcpIpConfig() {

		return new TcpIpConfig() //
				.setEnabled(true) //
				.setMembers(Arrays //
						.asList(StringUtils.split(members, ",")) //
						.stream() //
						.map(StringUtils::trimToNull) //
						.filter(Objects::nonNull) //
						.collect(Collectors.toList()));
	}

	private MulticastConfig multicastConfig() {
		return new MulticastConfig() //
				.setEnabled(true) //
				.setMulticastGroup("224.2.2.4") //
				.setLoopbackModeEnabled(true) //
				.setMulticastTimeoutSeconds(2) //
				.setMulticastTimeToLive(32) //
				.setMulticastPort(6666);
	}

	private MapConfig mapConfig(final DocumentCatalogResolver documentCatalogResolver) {
		return new MapConfig(documentCatalogResolver.getCatalogName());
	}
}
