package com.amberlight.cloud.gateway.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @RequestMapping("/")
    public String helloAdmin() {
        return "hello my brother from another mother :)";
    }

}