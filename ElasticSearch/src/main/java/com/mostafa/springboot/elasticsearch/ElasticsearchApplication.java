package com.mostafa.springboot.elasticsearch;

import com.mostafa.springboot.elasticsearch.config.ElasticSearchProperties;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
    public Client client() throws UnknownHostException {

        Settings elasticsearchSettings = Settings.builder()
                .put("client.transport.sniff", true)
//                .put("path.home", elasticSearchProperties.getHost())
                .put("cluster.name", elasticSearchProperties.getClustername()).build();
        return new PreBuiltTransportClient(elasticsearchSettings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticSearchProperties.getHost()), elasticSearchProperties.getPort()));
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(client());
    }
}
