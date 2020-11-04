package com.amberlight.blog.gateway.auth.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(String token);

}
