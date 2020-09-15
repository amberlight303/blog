package com.amberlight.cloud.gateway.auth.security.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(String token);

}
