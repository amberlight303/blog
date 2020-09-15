package com.amberlight.cloud.svcpost.post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Service
@Transactional(readOnly = true)
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Post findPostById(Long postId) {
        return Optional.ofNullable(postRepository.getOne(postId))
            .orElseThrow(() -> new PostNotFoundException("Post not found. ID: " + postId));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Post createPost(Post post) {
        final Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setUserId(post.getUserId());
        return postRepository.save(newPost);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Post updatePost(Map<String, String> updates, Long postId) {
        final Post post = findPostById(postId);
        updates.keySet()
            .forEach(key -> {
                switch (key) {
                case "userId":
                    post.setUserId(Long.valueOf(updates.get(key)));
                    break;
                case "title":
                    post.setTitle(updates.get(key));
                }
            });
        return postRepository.save(post);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Post updatePost(Post post, Long postId) {
        Preconditions.checkNotNull(post);
        Preconditions.checkState(post.getId().equals(postId));
        Preconditions.checkNotNull(postRepository.getOne(postId));
        return postRepository.save(post);
    }
}
