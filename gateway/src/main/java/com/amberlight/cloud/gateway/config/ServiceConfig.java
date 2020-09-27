package com.amberlight.cloud.gateway.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.amberlight.cloud.gateway.auth", "com.amberlight.cloud.struct.security" })
public class ServiceConfig {

}