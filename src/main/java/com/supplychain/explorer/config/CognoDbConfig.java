package com.supplychain.explorer.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CognoDbConfig {

    private final CognoDbProperties properties;

    public CognoDbConfig(CognoDbProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Driver neo4jDriver() {
        return GraphDatabase.driver(
                properties.getUri(),
                AuthTokens.basic(
                        properties.getUsername(),
                        properties.getPassword()
                )
        );
    }
}