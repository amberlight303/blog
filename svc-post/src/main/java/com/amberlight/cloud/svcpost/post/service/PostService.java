package com.amberlight.cloud.svcpost.post.service;

import com.amberlight.cloud.svcpost.post.model.domain.Post;
import com.amberlight.cloud.svcpost.post.model.dto.PostDto;

import java.util.List;

public interface PostService {

    List<Post> findAllPosts();

    Post findPostById(String postId);

    Post createPost(Post post);

    void deletePost(String postId);

    Post updatePost(Post post, String postId);
}
