package com.amberlight.blog.svcpost.post.service;

import com.amberlight.blog.svcpost.post.model.domain.Post;

import java.util.List;

public interface PostElasticService {

    Post savePostWithId(Post postDto);

    List<Post> findByTitleOrContentAllIgnoreCase(String keyword);

    List<Post> findAllByUserId(Long userId);

    void deleteById(String postId);

}
