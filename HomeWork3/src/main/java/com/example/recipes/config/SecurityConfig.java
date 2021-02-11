package com.example.recipes.config;

import com.example.recipes.model.Role;
import com.example.recipes.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(POST, "/login").permitAll()
                .antMatchers(GET, "/register").permitAll()
                .antMatchers(GET, "/recipe-form").authenticated()
                .antMatchers(POST, "/recipe-form").authenticated()
                .antMatchers("/user-form").hasAnyRole(Role.ADMIN.toString())
                .antMatchers("/users").hasAnyRole(Role.ADMIN.toString())
                .antMatchers("/").permitAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/recipes")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userService::getUserByUsername;
    }
}