package com.springspeed.userbehavior.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Elasticsearch配置类
 * 配置Elasticsearch的连接和客户端
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.springspeed.userbehavior.repository")
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    /**
     * 配置Elasticsearch的RestHighLevelClient
     * @return RestHighLevelClient
     */
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        // 从配置文件中读取Elasticsearch的连接地址和端口
        // 这里暂时使用默认的连接地址和端口
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    /**
     * 配置ElasticsearchOperations
     * @return ElasticsearchOperations
     */
    @Bean
    public ElasticsearchOperations elasticsearchOperations() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}