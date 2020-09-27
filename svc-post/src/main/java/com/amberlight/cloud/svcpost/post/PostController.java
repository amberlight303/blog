package com.amberlight.cloud.svcpost.post;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.amberlight.cloud.svcpost.post.service.PostServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private Gson gson = new Gson();

    @Autowired
    private PostServiceImpl postService;

//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN','ROLE_SYSTEM')")
    @GetMapping
    public List<Post> findAllPosts(Principal principal, @RequestHeader Map<String, String> headers) {
        System.out.println("*********************************** PRINCIPAL IS: \n" + gson.toJson(principal));


        Object principalObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("=================================== PRINCIPAL IS: \n" + gson.toJson(principalObject));

        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });

        return postService.findAllPosts();
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_SYSTEM')")
//    @Secured("ADMIN")
//    @Secured("ROLE_EBANUMBA")
    @GetMapping("/{postId}")
    public Post findPost(@PathVariable String postId) {
        return postService.findPostById(postId);
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN','ROLE_SYSTEM')")
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYSTEM')")
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable String postId) {
        postService.deletePost(postId);
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYSTEM')")
    @PutMapping("/{postId}")
    public Post updatePost(@RequestBody Post post, @PathVariable String postId) {
        return postService.updatePost(post, postId);
    }

}
