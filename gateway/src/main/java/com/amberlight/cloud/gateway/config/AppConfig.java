package com.amberlight.cloud.gateway.config;

import com.amberlight.cloud.gateway.auth.security.security.ActiveUserStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ActiveUserStore activeUserStore() {
        return new ActiveUserStore();
    }

}