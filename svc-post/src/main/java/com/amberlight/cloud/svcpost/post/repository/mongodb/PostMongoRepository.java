package com.amberlight.cloud.svcpost.post.repository.mongodb;

import com.amberlight.cloud.svcpost.post.model.domain.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMongoRepository extends CrudRepository<Post, String> {

}
