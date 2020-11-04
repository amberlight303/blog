package com.amberlight.blog.gateway.config;


import com.amberlight.blog.struct.log4j2.ThreadLogContextFilter;
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
