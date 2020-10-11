package com.amberlight.cloud.svcpost.post.listener;

import com.amberlight.cloud.svcpost.config.KafkaProducerConfig;
import com.amberlight.cloud.svcpost.post.model.domain.Post;
import com.amberlight.cloud.svcpost.post.service.PostElasticService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostElasticService postElasticService;

    @KafkaListener(
            topics = "${kafka.topic.elasticsearch.name}",
            groupId = "${kafka.consumersGroup.elasticsearch.groupId}"
    )
    public void receive(@Payload String data, @Header("X-Command") String commandHeader) throws JsonProcessingException {
        if (commandHeader == null) throw new IllegalStateException("X-Command header cannot be null");
        if (data == null) throw new IllegalStateException("Data cannot be null");
        Post post = objectMapper.readValue(data, Post.class);
        switch (commandHeader) {
            case KafkaProducerConfig.X_COMMAND_SAVE:
                postElasticService.savePostWithId(post);
                break;
            case KafkaProducerConfig.X_COMMAND_DELETE:
                postElasticService.deleteById(post.getId());
                break;
            default:
                throw new IllegalStateException("Unknown X-Command header");
        }



    }


}
