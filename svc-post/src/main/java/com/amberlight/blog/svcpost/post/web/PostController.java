package com.amberlight.blog.svcpost.post.web;

import java.util.List;

import com.amberlight.blog.struct.exception.BusinessLogicException;
import com.amberlight.blog.struct.log4j2.CustomMessage;
import com.amberlight.blog.struct.log4j2.LogLevel;
import com.amberlight.blog.svcpost.post.model.domain.Post;
import com.amberlight.blog.svcpost.post.model.dto.PostDto;
import com.amberlight.blog.svcpost.post.service.IAuthenticationFacade;
import com.amberlight.blog.svcpost.post.service.PostElasticService;
import com.amberlight.blog.svcpost.post.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PostController {

    private static final Logger logger = LogManager.getLogger(PostController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IAuthenticationFacade authFacade;

    @Autowired
    private PostService postService;

    @Autowired
    private PostElasticService postElasticService;

    @GetMapping("/posts/test")
    public ResponseEntity<String> test() {
        logger.log(LogLevel.DIAG, new CustomMessage(1, "DIAG MESSAGE BEFORE EXCEPTION"));
//        return ResponseEntity.ok("hello boi, this is a test!");
        throw new BusinessLogicException(1, "SOME BUSINESS LOGIC EXCEPTION TEXT");
    }

    @GetMapping("/posts/one-more-test")
    public ResponseEntity<String> oneMoreTest() {
        logger.log(LogLevel.DIAG, new CustomMessage(1, "IT IS A NEW DIAG MESSAGE!"));
        return ResponseEntity.ok("one more test message");
//        throw new BusinessLogicException(1, "SOME BUSINESS LOGIC EXCEPTION TEXT");
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> findAllPosts() {
        return ResponseEntity.ok(postService.findAllPosts());
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> findPost(@PathVariable String postId) {
        return ResponseEntity.ok(postService.findPostById(postId));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYSTEM')")
    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody @Valid PostDto post) throws JsonProcessingException {
        post.setUserId(authFacade.getUser().getId());
        return ResponseEntity.ok(postService.createPost(post));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYSTEM')")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) throws JsonProcessingException {
        postService.deletePost(postId, authFacade.getUser().getId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYSTEM')")
    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> updatePost(@RequestBody @Valid PostDto post,
                           @PathVariable String postId) throws JsonProcessingException {
        post.setUserId(authFacade.getUser().getId());
        return ResponseEntity.ok(postService.updatePost(post, postId));
    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<Post>> searchByTitleOrContentAllIgnoreCase(@RequestParam("s") String keyword) {
        return ResponseEntity.ok(postElasticService.findByTitleOrContentAllIgnoreCase(keyword));
    }

    @GetMapping("/posts/search/{userId}")
    public ResponseEntity<List<Post>> searchAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postElasticService.findAllByUserId(userId));
    }

}