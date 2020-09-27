package com.amberlight.cloud.svcpost.post.repository;

import com.amberlight.cloud.svcpost.post.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, String> {

}
