package com.amberlight.blog.svcpost.post.repository.elasticsearch;

import com.amberlight.blog.svcpost.post.model.domain.Post;
import org.springframework.stereotype.Repository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


import java.util.List;

@Repository
public interface PostElasticRepository extends ElasticsearchRepository<Post, String> {

//    List<Post> findByTitleOrContentAllIgnoreCase(String keyword);

    List<Post> findAllByUserId(Long userId);
}
