package com.thoughtmechanix.organization.security;

import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * The class to define the access control rules.
 * 
 * @author  Wuyi Chen
 * @date    04/06/2019
 * @version 1.0
 * @since   1.0
 */
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
//                .antMatchers(HttpMethod.DELETE, "/v1/organizations/**")
//                .hasRole("ADMIN")
                .anyRequest()
                .authenticated();
    }
}
