package com.amberlight.blog.gateway.web;

import com.amberlight.blog.struct.exception.BusinessLogicException;
import com.amberlight.blog.struct.log4j2.CustomMessage;
import com.amberlight.blog.struct.log4j2.LogLevel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger logger = LogManager.getLogger(TestController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Secured("ROLE_ADMIN")
    @RequestMapping("/hello-to-admin")
    public ResponseEntity<String> helloAdmin() {
        logger.info("HELLO ADMIN TEST INFO MESSAGE");
        logger.debug("HELLO ADMIN TEST DEBUG MESSAGE");
        logger.warn("HELLO ADMIN TEST WARN MESSAGE");
        logger.log(LogLevel.DIAG, new CustomMessage(1, "HELLO ADMIN TEST DIAG CUSTOm MESSAGE"));
        throw new BusinessLogicException(1, "Gateway helloAdmin BusinessLogicException");
    }

}