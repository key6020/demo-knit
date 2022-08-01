package com.task.demo2.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .authorizeHttpRequests()
                .antMatchers("/v1/**", "/h2-console/**", "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/v1/user/signup", "/v1/user/login", "/actuator/health", "/upload", "/v1/threads/list", "/v1/thread/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
