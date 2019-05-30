package br.com.cafebinario.documentpropagation.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import br.com.cafebinario.documentpropagation.domain.DocumentCatalogResolver;

@Configuration
public class HazelcastConfiguration {

	private static final String HAZELCAST_INSTANCE_NAME = "DocumentPropagation";

	@Bean
	public Config config(@Autowired final DocumentCatalogResolver documentCatalogResolver) {
		return new Config(HAZELCAST_INSTANCE_NAME) //
				.setNetworkConfig(networkConfig()) //
				.addMapConfig(mapConfig(documentCatalogResolver));
	}

	@Bean(destroyMethod = "shutdown")
	public HazelcastInstance hazelcastInstance(@Autowired final Config config) {
		return Hazelcast.getOrCreateHazelcastInstance(config);
	}

	private NetworkConfig networkConfig() {
		return new NetworkConfig() //
				.setJoin(join());
	}

	private JoinConfig join() {
		return new JoinConfig() //
				.setMulticastConfig(multicastConfig());
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
