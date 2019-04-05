package com.thoughtmechanix.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * The bootstrap class for the authentication service.
 * 
 * @author  Wuyi Chen
 * @date    04/04/2019
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@RestController
@EnableResourceServer
@EnableAuthorizationServer                                             // This annotation tells Spring Cloud that this service will act as an OAuth2 service
public class Application {
    @RequestMapping(value = {"/user"}, produces = "application/json")  // The endpoint which the protected service to validate the token sent by the client
    public Map<String, Object> user(OAuth2Authentication user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user",        user.getUserAuthentication().getPrincipal());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
        return userInfo;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
