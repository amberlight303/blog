package com.amberlight.blog.gateway.auth.security.security.google2fa;

import com.amberlight.blog.struct.security.CustomWebAuthenticationDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Override
    @JsonIgnore
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new CustomWebAuthenticationDetails(context);
    }

}