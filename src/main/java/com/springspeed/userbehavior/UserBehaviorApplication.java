package com.springspeed.userbehavior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 电商用户行为分析与用户分群系统的启动类
 */
@SpringBootApplication(scanBasePackages = "com.springspeed.userbehavior")
@EntityScan(basePackages = "com.springspeed.userbehavior.entity")
@EnableJpaRepositories(basePackages = "com.springspeed.userbehavior.repository")
@EnableElasticsearchRepositories(basePackages = "com.springspeed.userbehavior.repository")
@EnableScheduling
public class UserBehaviorApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserBehaviorApplication.class, args);
    }
}