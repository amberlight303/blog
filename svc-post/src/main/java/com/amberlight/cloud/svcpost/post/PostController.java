package com.amberlight.cloud.svcpost.post;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> findAllPosts() {
        return postService.findAllPosts();
    }

    @GetMapping("/{postId}")
    public Post findPost(@PathVariable Long postId) {
        return postService.findPostById(postId);
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@RequestBody Post post, @PathVariable Long postId) {
        return postService.updatePost(post, postId);
    }

    @PatchMapping("/{postId}")
    public Post updatePost(@RequestBody Map<String, String> updates, @PathVariable Long postId) {
        return postService.updatePost(updates, postId);
    }
}
