package com.amberlight.blog.gateway.auth.security.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(String token);

}
