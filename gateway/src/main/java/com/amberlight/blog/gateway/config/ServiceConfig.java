package com.amberlight.blog.gateway.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.amberlight.blog.gateway.auth", "com.amberlight.blog.struct.security"})
public class ServiceConfig {

}