package com.amberlight.cloud.svcpost;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableEurekaClient
@EnableMongoRepositories(basePackages = {"com.amberlight.cloud.svcpost.post.repository.mongodb"})
@EnableElasticsearchRepositories(basePackages = {"com.amberlight.cloud.svcpost.post.repository.elasticsearch"})
public class PostServiceApplication {

    @Autowired
    private EurekaClient eurekaClient;

    public static void main(String[] args) {
        SpringApplication.run(PostServiceApplication.class, args);
    }

}
