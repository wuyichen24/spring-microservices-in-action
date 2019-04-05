package com.thoughtmechanix.authentication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The configuration class to define the authentication information at 
 * user-level.
 * 
 * @author  Wuyi Chen
 * @date    04/04/2019
 * @version 1.0
 * @since   1.0
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	@Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("john.carnell").password("password1").roles("USER")                 // Define the first user: john.carnell with the password "password1" and the role "USER"
                .and()
                .withUser("william.woodward").password("password2").roles("USER", "ADMIN");   // Define the second user: william.woodward with the password "password2" and the role "ADMIN"
    }
}
