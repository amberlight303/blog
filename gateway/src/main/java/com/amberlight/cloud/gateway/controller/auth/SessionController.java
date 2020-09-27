package com.amberlight.cloud.gateway.controller.auth;

import com.google.gson.Gson;
import com.netflix.zuul.context.RequestContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@RestController
public class SessionController {

    private Gson gson = new Gson();

//    @Secured("ROLE_ADMIN")
    @Secured("ROLE_ADMIN")
    @RequestMapping("/hello-to-admin")
    public String helloAdmin(Principal principal, @RequestHeader Map<String, String> headers) {

//        RequestContext context = RequestContext.getCurrentContext();
//        HttpSession httpSession = context.getRequest().getSession();


//        System.out.println("88888888888888888888888888888888888 http session id is: " + httpSession.getId());



        System.out.println("################################### PRINCIPAL (PRINCIPAL) IS: \n" + gson.toJson(principal));


        Object principalObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("*********************************** current session ID IS: "
                + RequestContextHolder.currentRequestAttributes().getSessionId());

        System.out.println("=================================== PRINCIPAL (FROM SecurityContextHolder) IS: \n"
                + gson.toJson(principalObject));

        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });


        return "hello my brother from another mother :)";
    }










}