package com.amberlight.blog.gateway.controller;

import com.amberlight.blog.struct.log4j2.CustomMessage;
import com.amberlight.blog.struct.log4j2.LogLevel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.security.Principal;
import java.util.Map;

@RestController
public class TestController {

    private static final Logger logger = LogManager.getLogger(TestController.class);

    @Autowired
    private ObjectMapper objectMapper;

//    @Secured("ROLE_ADMIN")
    @Secured("ROLE_ADMIN")
    @RequestMapping("/hello-to-admin")
    public String helloAdmin(Principal principal, @RequestHeader Map<String, String> headers) throws JsonProcessingException {



//        RequestContext context = RequestContext.getCurrentContext();
//        HttpSession httpSession = context.getRequest().getSession();


//        System.out.println("88888888888888888888888888888888888 http session id is: " + httpSession.getId());

//
//
//        System.out.println("################################### PRINCIPAL (PRINCIPAL) IS: \n" + objectMapper.writeValueAsString(principal));
//
//
//        Object principalObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        System.out.println("*********************************** current session ID IS: "
//                + RequestContextHolder.currentRequestAttributes().getSessionId());
//
//        System.out.println("=================================== PRINCIPAL (FROM SecurityContextHolder) IS: \n"
//                + objectMapper.writeValueAsString(principalObject));
//
//        headers.forEach((key, value) -> {
//            System.out.println(String.format("Header '%s' = %s", key, value));
//        });


        logger.info("SUKA_SUKA_SUKA_SUKA_SUKA_SUKA_SUKA_SUKA_SUKA_SUKA_SUKA_SUKA");
        logger.debug("GNIDA_GNIDA_GNIDA_GNIDA_GNIDA_GNIDA_GNIDA_GNIDA_GNIDA_GNIDA");

        logger.log(LogLevel.DIAG, new CustomMessage(1, "PEDRO_PEDRO_PEDRO_PEDRO_PEDRO_PEDRO_PEDRO_PEDRO_PEDRO"));


        return "hello my brother from another mother :)";
    }










}