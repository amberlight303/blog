package com.amberlight.blog.gateway.web;

import com.amberlight.blog.struct.exception.BusinessLogicException;
import com.amberlight.blog.struct.log4j2.CustomMessage;
import com.amberlight.blog.struct.log4j2.LogLevel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class TestController {

    private static final Logger logger = LogManager.getLogger(TestController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Secured("ROLE_ADMIN")
    @RequestMapping("/hello-to-admin")
    public String helloAdmin(Principal principal, @RequestHeader Map<String, String> headers) throws JsonProcessingException {


        logger.info("SUKA_SUKA_SUKA_SUKA_SUKA_SUKA_SUKA_SUKA_SUKA_SUKA_SUKA_SUKA");
        logger.debug("GNIDA_GNIDA_GNIDA_GNIDA_GNIDA_GNIDA_GNIDA_GNIDA_GNIDA_GNIDA");
        logger.warn("THIS IS WARNING BABY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        logger.log(LogLevel.DIAG, new CustomMessage(1, "PEDRO_PEDRO_PEDRO_PEDRO_PEDRO_PEDRO_PEDRO_PEDRO_PEDRO"));


//        return "hello my brother from another mother :)";

        throw new BusinessLogicException(1, "ZUZUZU TESTO BusinessLogicException");
    }










}