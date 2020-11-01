package com.amberlight.blog.gateway.client.post;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "post-service")
public interface PostsClient {

    @RequestMapping(value = "/posts/{postId}", method = {RequestMethod.GET})
    Post getPostById(@PathVariable("postId") Long postId);

}
