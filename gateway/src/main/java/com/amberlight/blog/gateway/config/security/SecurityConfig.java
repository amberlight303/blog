package com.amberlight.blog.gateway.config.security;


import com.amberlight.blog.gateway.auth.security.CustomRememberMeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.rememberme.key}")
    private String rememberMeKey;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
            .failureHandler(authenticationFailureHandler)
            .and()
            .authorizeRequests()
            .antMatchers("/eureka/**").hasRole("SYSTEM")
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and()
            .rememberMe().rememberMeServices(rememberMeServices()).key(rememberMeKey)
            .and()
            .anonymous().disable()
            .csrf().disable();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        return new CustomRememberMeServices(rememberMeKey, userDetailsService, new InMemoryTokenRepositoryImpl());
    }

}