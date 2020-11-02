package com.amberlight.blog.svcpost.post.listener;

import com.amberlight.blog.svcpost.config.KafkaProducerConfig;
import com.amberlight.blog.struct.log4j2.CustomMessage;
import com.amberlight.blog.struct.log4j2.LogLevel;
import com.amberlight.blog.svcpost.post.model.domain.Post;
import com.amberlight.blog.svcpost.post.service.PostElasticService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class ElasticsearchListener {

    private final static Logger logger = LogManager.getLogger(ElasticsearchListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostElasticService postElasticService;

    @KafkaListener(
            topics = "${kafka.topic.elasticsearch.name}",
            groupId = "${kafka.consumersGroup.elasticsearch.groupId}"
    )
    public void receive(@Payload String data, @Header("X-Command") String commandHeader) throws JsonProcessingException {
        ThreadContext.put("LRID", UUID.randomUUID().toString());
        if (commandHeader == null) throw new IllegalStateException("X-Command header cannot be null");
        if (data == null) throw new IllegalStateException("Data cannot be null");
        Post post = objectMapper.readValue(data, Post.class);
        switch (commandHeader) {
            case KafkaProducerConfig.X_COMMAND_SAVE:
                postElasticService.savePostWithId(post);
                logger.log(LogLevel.DIAG, new CustomMessage(5, "Post saved to elasticsearch",
                           new HashMap<String, Object>() {{put("post", post);}}));
                break;
            case KafkaProducerConfig.X_COMMAND_DELETE:
                postElasticService.deleteById(post.getId());
                logger.log(LogLevel.DIAG, new CustomMessage(6, "Post deleted from elasticsearch",
                        new HashMap<String, Object>() {{put("post", post);}}));
                break;
            default:
                throw new IllegalStateException("Unknown X-Command header");
        }
    }

}
