package com.amberlight.blog.svcpost.post.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.amberlight.blog.struct.exception.BusinessLogicException;
import com.amberlight.blog.svcpost.config.KafkaProducerConfig;
import com.amberlight.blog.struct.log4j2.CustomMessage;
import com.amberlight.blog.struct.log4j2.LogLevel;
import com.amberlight.blog.svcpost.post.model.domain.Post;
import com.amberlight.blog.svcpost.post.model.dto.PostDto;
import com.amberlight.blog.svcpost.post.repository.mongodb.PostMongoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LogManager.getLogger(PostServiceImpl.class);

    @Value(value = "${kafka.topic.elasticsearch.name}")
    private String kafkaElasticsearchTopic;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private PostMongoRepository postRepository;

    @Override
    public List<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        return posts;
    }

    @Override
    public Post findPostById(String postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public Post createPost(PostDto post) throws JsonProcessingException {
        Post newPost =  new Post();
        newPost.setUserId(post.getUserId());
        newPost.setTitle(post.getTitle());
        newPost.setPreviewContent(post.getPreviewContent());
        newPost.setContent(post.getContent());
        newPost.setCreatedDate(new Date());
        Post savedPost = postRepository.save(newPost);
        List<Header> headers = new ArrayList<>();
        headers.add(KafkaProducerConfig.HEADER_COMMAND_SAVE);
        ProducerRecord<String, String> savePostRecord = new ProducerRecord<>(kafkaElasticsearchTopic, null,
                                                savedPost.getId(), objectMapper.writeValueAsString(savedPost), headers);
        kafkaTemplate.send(savePostRecord);
        logger.log(LogLevel.BUSINESS, new CustomMessage(1, "Post created"));
        return savedPost;
    }

    @Override
    public void deletePost(String postId, Long userId) throws JsonProcessingException {
        Post post = findPostById(postId);
        if (post == null) throw new BusinessLogicException("Post doesn't exist");
        if (!post.getUserId().equals(userId)) throw new AccessDeniedException("Access to the operation denied");
        postRepository.deleteById(postId);
        Post postToDelete = new Post(postId);
        List<Header> headers = new ArrayList<>();
        headers.add(KafkaProducerConfig.HEADER_COMMAND_DELETE);
        ProducerRecord<String, String> savePostRecord = new ProducerRecord<>(kafkaElasticsearchTopic, null,
                                        postToDelete.getId(), objectMapper.writeValueAsString(postToDelete), headers);
        kafkaTemplate.send(savePostRecord);
        logger.log(LogLevel.BUSINESS, new CustomMessage(3, "Post deleted"));
    }

    @Override
    public Post updatePost(PostDto post, String postId) throws JsonProcessingException {
        Optional<Post> dbPost = postRepository.findById(postId);
        if (dbPost.isPresent()) {
            if (!dbPost.get().getUserId().equals(post.getUserId()))
                throw new BusinessLogicException(-2, "Access to the operation denied");
            Post postForUpdate =  new Post();
            postForUpdate.setId(postId);
            postForUpdate.setUserId(dbPost.get().getUserId());
            postForUpdate.setTitle(post.getTitle());
            postForUpdate.setPreviewContent(post.getPreviewContent());
            postForUpdate.setContent(post.getContent());
            postForUpdate.setCreatedDate(dbPost.get().getCreatedDate());
            postForUpdate.setModifiedDate(new Date());
            Post updatedPost = postRepository.save(postForUpdate);
            List<Header> headers = new ArrayList<>();
            headers.add(KafkaProducerConfig.HEADER_COMMAND_SAVE);
            ProducerRecord<String, String> savePostRecord = new ProducerRecord<>(kafkaElasticsearchTopic, null,
                    updatedPost.getId(), objectMapper.writeValueAsString(updatedPost), headers);
            kafkaTemplate.send(savePostRecord);
            logger.log(LogLevel.BUSINESS, new CustomMessage(2, "Post updated"));
            return updatedPost;
        } else {
            throw new BusinessLogicException(-1, "Post doesn't exist");
        }
    }

}
