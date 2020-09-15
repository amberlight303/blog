package com.amberlight.cloud.gateway.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({ "com.amberlight.cloud.gateway.auth.task" })
public class SpringTaskConfig {

}
