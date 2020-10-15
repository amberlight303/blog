package com.amberlight.cloud.svcpost.config;


import com.amberlight.cloud.svcpost.post.filter.ThreadLogContextFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean createThreadLogContextFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new ThreadLogContextFilter());
        return registrationBean;
    }

}