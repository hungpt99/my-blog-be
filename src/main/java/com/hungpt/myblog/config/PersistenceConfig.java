package com.hungpt.myblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@Import(JpaRepositoryConfigExtension.class)
public class PersistenceConfig {


}
