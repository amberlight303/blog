package com.amberlight.cloud.svcpost.post.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.amberlight.cloud.svcpost.post.exception.EntityNotFoundException;
import com.amberlight.cloud.svcpost.post.model.domain.Post;
import com.amberlight.cloud.svcpost.post.model.dto.PostDto;
import com.amberlight.cloud.svcpost.post.repository.mongodb.PostMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

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
    public Post createPost(Post post) {
        Post newPost =  new Post();
        newPost.setUserId(post.getUserId());
        newPost.setTitle(post.getTitle());
        newPost.setPreviewContent(post.getPreviewContent());
        newPost.setContent(post.getContent());
        newPost.setCreatedDate(new Date());
        Post savedPost = postRepository.save(newPost);
        postElasticService.savePostWithId(savedPost);
        return savedPost;
    }

    @Override
    public void deletePost(String postId, Long userId) {
        Post post = findPostById(postId);
        if (post == null) throw new EntityNotFoundException("Post doesn't exist");
        if (!post.getUserId().equals(userId)) throw new AccessDeniedException("Access to the operation denied");
        postRepository.deleteById(postId);
        postElasticService.deleteById(postId);
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
            throw new EntityNotFoundException("Post doesn't exist");
        }
    }




}
