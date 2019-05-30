package br.com.cafebinario.documentpropagation.configurations;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients("br.com.cafebinario.documentpropagation.clients")
@Configuration
public class FeignAutoConfiguration {

}
