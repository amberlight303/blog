package com.amberlight.cloud.svcpost.post.service;

import com.amberlight.cloud.svcpost.post.model.domain.Post;
import com.amberlight.cloud.svcpost.post.model.dto.PostDto;

import java.util.List;

public interface PostElasticService {

    Post savePostWithId(Post postDto);

    List<Post> findByTitleOrContentAllIgnoreCase(String keyword);

    List<Post> findAllByUserId(Long userId);

    void deleteById(String postId);

}
