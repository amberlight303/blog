package com.amberlight.cloud.svcpost.post.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.amberlight.cloud.struct.exception.BusinessLogicException;
import com.amberlight.cloud.svcpost.config.KafkaProducerConfig;
import com.amberlight.cloud.svcpost.post.model.domain.Post;
import com.amberlight.cloud.svcpost.post.repository.mongodb.PostMongoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

//    private static final Logger LOG = LoggerFactory.getLogger(PostServiceImpl.class);

    @Value(value = "${kafka.topic.elasticsearch.name}")
    private String kafkaElasticsearchTopic;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private PostMongoRepository postRepository;

    @Autowired
    private PostElasticService postElasticService;

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
    public Post createPost(Post post) throws JsonProcessingException {
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
    }

    @Override
    public Post updatePost(Post post, String postId, Long userId) {
        Optional<Post> dbPost = postRepository.findById(postId);
        if (dbPost.isPresent()) {
            if (!dbPost.get().getUserId().equals(userId))
                throw new AccessDeniedException("Access to the operation denied");
            Post postForUpdate =  new Post();
            postForUpdate.setUserId(dbPost.get().getUserId());
            postForUpdate.setTitle(post.getTitle());
            postForUpdate.setPreviewContent(post.getPreviewContent());
            postForUpdate.setContent(post.getContent());
            postForUpdate.setCreatedDate(dbPost.get().getCreatedDate());
            postForUpdate.setModifiedDate(new Date());
            Post updatedPost = postRepository.save(postForUpdate);
            postElasticService.savePostWithId(updatedPost);
            return updatedPost;
        } else {
            throw new BusinessLogicException("Post doesn't exist");
        }
    }

}
