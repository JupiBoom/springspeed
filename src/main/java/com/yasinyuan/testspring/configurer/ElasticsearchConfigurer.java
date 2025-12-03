package com.yasinyuan.testspring.configurer;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Elasticsearch 配置类
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.yasinyuan.testspring.repository")
public class ElasticsearchConfigurer {
    
    @Bean
    public Client client() throws UnknownHostException {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true)
                .build();
        
        TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        
        return client;
    }
    
    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(client());
    }
}