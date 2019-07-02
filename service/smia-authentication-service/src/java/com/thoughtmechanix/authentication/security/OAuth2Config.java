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
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * The configuration class to define the authentication information at 
 * application-level.
 * 
 * @author  Wuyi Chen
 * @date    04/04/2019
 * @version 1.0
 * @since   1.0
 */
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {            // Define what client applications are registered with the service
// Store in memory
//        clients.inMemory()                                                                      // Store the application information in memory
//                .withClient("eagleeye")                                                         // Specify which client application will register
//                .secret("thisissecret")                                                         // Specify the secret which will be used to get the access token
//                .authorizedGrantTypes("refresh_token", "password", "client_credentials")        // Provide a list of the authorization grant types that will be supported by the service
//                .scopes("webclient", "mobileclient");                                           // Define the types of the client applications can get the access token from the service
    	
// Store in database
    	clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	endpoints
    			.authenticationManager(authenticationManager)
    			.userDetailsService(userDetailsService);
    }
}
