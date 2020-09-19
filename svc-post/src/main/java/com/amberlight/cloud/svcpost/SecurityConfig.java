package com.amberlight.cloud.svcpost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //try in memory auth with no users to support the case that this will allow for users that are logged in to go anywhere
        auth.inMemoryAuthentication();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
            .disable()
        .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/posts").permitAll()
            .antMatchers(HttpMethod.GET, "/posts/*").permitAll()
            .antMatchers(HttpMethod.POST, "/posts").hasRole("ADMIN")
            .antMatchers(HttpMethod.PATCH, "/posts/*").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/posts/*").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
        .csrf()
            .disable();
    }
}
