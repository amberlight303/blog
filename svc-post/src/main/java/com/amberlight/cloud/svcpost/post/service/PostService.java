package com.amberlight.cloud.svcpost.post.service;

import com.amberlight.cloud.svcpost.post.Post;

import java.util.List;

public interface PostService {

    public List<Post> findAllPosts();

    public Post findPostById(String postId);

    public Post createPost(Post post);

    public void deletePost(String postId);

    public Post updatePost(Post post, String postId);
}
