package com.amberlight.blog.svcpost.post.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.amberlight.blog.struct.exception.BusinessLogicException;
import com.amberlight.blog.svcpost.config.log4j2.CustomMessage;
import com.amberlight.blog.svcpost.config.log4j2.LogLevel;
import com.amberlight.blog.svcpost.post.model.domain.Post;
import com.amberlight.blog.svcpost.post.service.IAuthenticationFacade;
import com.amberlight.blog.svcpost.post.service.PostElasticService;
import com.amberlight.blog.svcpost.post.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@Controller
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

    @GetMapping("/test")
    public String test() {

        logger.log(LogLevel.DIAG, new CustomMessage(1, "TEST TEXT FROM TEST METHOD"));

        return "hello boi, this is test";

//        throw new BusinessLogicException(1, "SOME BUSINESS LOGIC EXCEPTION TEXT");
    }


    @GetMapping
    public List<Post> findAllPosts() {
        return postService.findAllPosts();
    }

    @GetMapping("/{postId}")
    public Post findPost(@PathVariable String postId) {
        return postService.findPostById(postId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYSTEM')")
    @PostMapping
    public Post createPost(@RequestBody Post post) throws JsonProcessingException {
        return postService.createPost(post);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYSTEM')")
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable String postId) throws JsonProcessingException {
        postService.deletePost(postId, authFacade.getUser().getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYSTEM')")
    @PutMapping("/{postId}")
    public Post updatePost(@RequestBody Post post, @PathVariable String postId) throws JsonProcessingException {
        return postService.updatePost(post, postId, authFacade.getUser().getId());
    }

    @GetMapping("/search")
    public List<Post> searchByTitleOrContentAllIgnoreCase(@RequestParam("s") String keyword) {
        return postElasticService.findByTitleOrContentAllIgnoreCase(keyword);
    }

    @GetMapping("/search/{userId}")
    public List<Post> searchAllByUserId(@PathVariable Long userId) {
        return postElasticService.findAllByUserId(userId);
    }

}