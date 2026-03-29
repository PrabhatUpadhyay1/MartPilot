package org.martpilot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA and Database configuration
 */
@Configuration
@EnableJpaRepositories(basePackages = "org.martpilot.repository")
@EnableTransactionManagement
@EnableJpaAuditing
public class JpaConfig {

}

