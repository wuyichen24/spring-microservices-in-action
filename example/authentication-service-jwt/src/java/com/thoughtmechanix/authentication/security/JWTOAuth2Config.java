package com.thoughtmechanix.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

/**
 * The configuration class to define the authentication information at 
 * application-level and let Spring Security know to use JWT token.
 * 
 * @author  Wuyi Chen
 * @date    04/08/2019
 * @version 1.0
 * @since   1.0
 */
@Configuration
public class JWTOAuth2Config extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtTokenEnhancer, jwtAccessTokenConverter));   // Spring Security allows you to hook multiple token enhancers

        endpoints.tokenStore(tokenStore)                                                                  // The JWTTokenStoreConfig.tokenStore() will be injected in here
                .accessTokenConverter(jwtAccessTokenConverter)                                            // The JWTTokenStoreConfig.jwtAccessTokenConverter() will be injected in here
                .tokenEnhancer(tokenEnhancerChain)                                                        // The chain of token enhancers will be passed into the endpoint
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {                      // Define what client applications are registered with the service
        clients.inMemory()                                                                                // Store the application information in memory
                .withClient("eagleeye")                                                                   // Specify which client application will register
                .secret("thisissecret")                                                                   // Specify the secret which will be used to get the access token
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")                  // Provide a list of the authorization grant types that will be supported by the service
                .scopes("webclient", "mobileclient");                                                     // Define the types of the client applications can get the access token from the service
    }
}