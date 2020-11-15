package com.amberlight.blog.svcpost.config;

import lombok.Setter;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Setter
@Configuration
public class ElasticSearchConfig {

    @Value("${elastic-client.host}")
    private String host;

    @Value("${elastic-client.port}")
    private Integer port;

    @Value("${elastic-client.schema}")
    private String schema;

    @Value("${elastic-client.timeout}")
    private Integer timeout;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient elasticsearchClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, schema))
                .setRequestConfigCallback(
                        requestConfigBuilder -> requestConfigBuilder
                                .setConnectTimeout(timeout)
                                .setConnectionRequestTimeout(timeout)
                                .setSocketTimeout(timeout)));
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

}