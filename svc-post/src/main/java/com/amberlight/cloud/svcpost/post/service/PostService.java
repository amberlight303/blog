package com.amberlight.cloud.svcpost.post.service;

import com.amberlight.cloud.svcpost.post.model.domain.Post;
import com.amberlight.cloud.svcpost.post.model.dto.PostDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PostService {

    List<Post> findAllPosts();

    Post findPostById(String postId);

    Post createPost(Post post) throws JsonProcessingException;

    void deletePost(String postId, Long userId) throws JsonProcessingException;

    Post updatePost(Post post, String postId, Long userId);

}
