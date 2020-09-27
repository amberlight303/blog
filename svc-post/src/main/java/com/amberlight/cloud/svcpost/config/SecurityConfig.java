package com.amberlight.cloud.svcpost.config;

import com.amberlight.cloud.svcpost.security.CustomWebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // in memory auth with no users to support the case that this will allow for users that are logged in to go anywhere
        auth.inMemoryAuthentication();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//
//        http
////            .anonymous().disable()
////            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
////            .and()
//            .httpBasic().disable()
//            .authorizeRequests()
//          .antMatchers(HttpMethod.GET, "/posts").permitAll()
////          .antMatchers(HttpMethod.GET, "/posts/*").permitAll()
////          .antMatchers(HttpMethod.POST, "/posts").hasRole("ADMIN")
////          .antMatchers(HttpMethod.PATCH, "/posts/*").hasRole("ADMIN")
////          .antMatchers(HttpMethod.DELETE, "/posts/*").hasRole("ADMIN")
//            .anyRequest().authenticated()
//            .and()
//            .csrf()
//            .disable();

        http.
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/posts").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .formLogin()
                .authenticationDetailsSource(authenticationDetailsSource)
//                .disable()
        ;




    }






}
