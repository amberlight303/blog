package com.amberlight.blog.svcpost.post.service;

import com.amberlight.blog.svcpost.post.model.domain.Post;
import com.amberlight.blog.svcpost.post.model.dto.PostDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PostService {

    List<Post> findAllPosts();

    Post findPostById(String postId);

    Post createPost(PostDto post) throws JsonProcessingException;

    void deletePost(String postId, Long userId) throws JsonProcessingException;

    Post updatePost(PostDto post, String postId) throws JsonProcessingException;

}
