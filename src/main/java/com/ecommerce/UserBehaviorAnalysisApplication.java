package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ecommerce.repository")
@EnableElasticsearchRepositories(basePackages = "com.ecommerce.elasticsearch")
@EnableScheduling
public class UserBehaviorAnalysisApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserBehaviorAnalysisApplication.class, args);
    }
}
