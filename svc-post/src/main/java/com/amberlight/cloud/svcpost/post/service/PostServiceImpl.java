package com.amberlight.cloud.svcpost.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.amberlight.cloud.svcpost.post.Post;
import com.amberlight.cloud.svcpost.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

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
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setPreviewText(post.getPreviewText());
        return postRepository.save(post);
    }

    @Override
    public void deletePost(String postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public Post updatePost(Post post, String postId) {
        Optional<Post> dbPost = postRepository.findById(postId);
        if (dbPost.isPresent()) {
            Post updatedPost =  new Post();
            updatedPost.setTitle(post.getTitle());
            updatedPost.setContent(post.getContent());
            updatedPost.setPreviewText(post.getPreviewText());
            return postRepository.save(updatedPost);
        } else {
            throw new IllegalStateException("Post doesn't exist");
        }
    }

}
