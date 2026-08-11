package com.supplychain.explorer;

import com.supplychain.explorer.config.CognoDbProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CognoDbProperties.class)
public class SupplyChainExplorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupplyChainExplorerApplication.class, args);
	}
}