/*
 * Copyright 2019 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thoughtmechanix.authentication.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	@Autowired
    private DataSource dataSource;
	
	public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
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
// Store in memory
//        auth
//                .inMemoryAuthentication()
//                .withUser("john.carnell").password("password1").roles("USER")                 // Define the first user: john.carnell with the password "password1" and the role "USER"
//                .and()
//                .withUser("william.woodward").password("password2").roles("USER", "ADMIN");   // Define the second user: william.woodward with the password "password2" and the role "ADMIN"

// Store in databse
    	auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder);
    }
}
