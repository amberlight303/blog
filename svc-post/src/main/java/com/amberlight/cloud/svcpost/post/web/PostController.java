package com.amberlight.cloud.svcpost.post.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amberlight.cloud.struct.exception.BusinessLogicException;
import com.amberlight.cloud.struct.security.User;
import com.amberlight.cloud.svcpost.config.log4j2.CustomMessage;
import com.amberlight.cloud.svcpost.config.log4j2.LogLevel;
import com.amberlight.cloud.svcpost.post.model.domain.Post;
import com.amberlight.cloud.svcpost.post.service.IAuthenticationFacade;
import com.amberlight.cloud.svcpost.post.service.PostElasticService;
import com.amberlight.cloud.svcpost.post.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN','ROLE_SYSTEM')")
    @GetMapping
    public List<Post> findAllPosts(Authentication authentication, Principal principal, @RequestHeader Map<String, String> headers) throws JsonProcessingException {
/*
        System.out.println("*********************************** authentication IS: \n" + objectMapper.writeValueAsString(authentication));
        User authUser = (User) authentication.getPrincipal();
        System.out.println("*********************************** PRINCIPAL IS: \n" + objectMapper.writeValueAsString(principal));
        Object principalObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principalObject;
        System.out.println("user.getId(): " + user.getId());
        System.out.println("=================================== PRINCIPAL IS: \n" + objectMapper.writeValueAsString(principalObject));
        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });
*/
        return postService.findAllPosts();
    }


//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_SYSTEM')")
//    @Secured("ADMIN")
//    @Secured("ROLE_EBANUMBA")
    @GetMapping("/{postId}")
    public Post findPost(@PathVariable String postId) {
        return postService.findPostById(postId);
    }


//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_SYSTEM')")
//    @Secured("ADMIN")
//    @Secured("ROLE_EBANUMBA")
    @GetMapping("/test")
    public String test() {

        logger.log(LogLevel.DIAG, new CustomMessage(1, "TESTO OF EXCEPTION"));

//        return "hello boi, this is test";

        throw new BusinessLogicException(1, "SOME BUSINESS LOGIC EXCEPTION TEXT");
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN','ROLE_SYSTEM')")
    @PostMapping
    public Post createPost(@RequestBody Post post) throws JsonProcessingException {
        return postService.createPost(post);
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYSTEM')")
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable String postId) throws JsonProcessingException {
        postService.deletePost(postId, authFacade.getUser().getId());
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYSTEM')")
    @PutMapping("/{postId}")
    public Post updatePost(@RequestBody Post post, @PathVariable String postId) {
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