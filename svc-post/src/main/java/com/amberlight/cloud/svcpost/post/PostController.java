package com.amberlight.cloud.svcpost.post;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.amberlight.cloud.struct.security.User;
import com.amberlight.cloud.svcpost.post.exception.EntityNotFoundException;
import com.amberlight.cloud.svcpost.post.model.domain.Post;
import com.amberlight.cloud.svcpost.post.model.dto.PostDto;
import com.amberlight.cloud.svcpost.post.service.IAuthenticationFacade;
import com.amberlight.cloud.svcpost.post.service.PostElasticService;
import com.amberlight.cloud.svcpost.post.service.PostService;
import com.amberlight.cloud.svcpost.post.service.PostServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private Gson gson = new Gson();

    @Autowired
    private IAuthenticationFacade authFacade;

    @Autowired
    private PostService postService;

    @Autowired
    private PostElasticService postElasticService;

//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN','ROLE_SYSTEM')")
    @GetMapping
    public List<Post> findAllPosts(Authentication authentication, Principal principal, @RequestHeader Map<String, String> headers) {
        System.out.println("*********************************** authentication IS: \n" + gson.toJson(authentication));

//        User authUser = (User) authentication.getPrincipal();

//        System.out.println("++++++++++++++ authUser: " + gson.toJson(authUser));


        System.out.println("*********************************** PRINCIPAL IS: \n" + gson.toJson(principal));



        Object principalObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = (User) principalObject;

        System.out.println("user.getId(): " + user.getId());


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


//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_SYSTEM')")
//    @Secured("ADMIN")
//    @Secured("ROLE_EBANUMBA")
    @GetMapping("/test")
    public String test() {
        return "hello boi, this is test";
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