package com.mostafa.springboot.elasticsearch;

import com.mostafa.springboot.elasticsearch.config.ElasticSearchProperties;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.mostafa.springboot.elasticsearch.repository")
@Configuration
public class ElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class, args);
    }

    @Autowired
    private ElasticSearchProperties elasticSearchProperties;


    @Bean
    RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration configuration = ClientConfiguration.create(elasticSearchProperties.getNodeAddress());
        RestHighLevelClient client = RestClients.create(configuration).rest();
        return client;
    }

    @Bean
    ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}
