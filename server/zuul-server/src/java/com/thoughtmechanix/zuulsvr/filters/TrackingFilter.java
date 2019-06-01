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
package com.thoughtmechanix.zuulsvr.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.thoughtmechanix.zuulsvr.config.ServiceConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The pre-filter class to inspect all incoming requests and check the 
 * correlation is existing or not.
 * 
 * <p>This pre-filter will inspect all the incoming requests to the gateway 
 * and determine whether there's an HTTP header called 
 * {@code tmx-correlation-id} present in the request.
 * <ul>
 *   <li>If the {@code tmx-correlation-id} isn't present on the HTTP header, 
 *   this class will generate and set the correlation ID.
 *   <li>If there's already a correlation ID present, this class will not do 
 *   anything with the correlation ID.
 * </ul>
 *
 * @author  Wuyi Chen
 * @date    03/24/2019
 * @version 1.0
 * @since   1.0
 */
@Component
public class TrackingFilter extends ZuulFilter{
    private static final int     FILTER_ORDER  = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger  logger        = LoggerFactory.getLogger(TrackingFilter.class);

    @Autowired
    FilterUtils filterUtils;
    
    @Autowired
    private ServiceConfig serviceConfig;

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;      // Type: Define the filter type is pre-filter
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;                     // Execution Order: Define the order of execution across multiple Filters
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;                    // Criteria: Define the filter will be invoked or not
    }

    @Override
    public Object run() {                        // Action: The action to be executed if the Criteria is met
        if (isCorrelationIdPresent()) {
           logger.debug("tmx-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId());
        } else{
            filterUtils.setCorrelationId(generateCorrelationId());
            logger.debug("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
        }
        
        if (isAccessTokenPresent()) {
        	filterUtils.setAuthToken(filterUtils.getAuthToken());     // Pass the access token to the downstream service which the access token is required (like organization service)
        }
        
        RequestContext ctx = RequestContext.getCurrentContext();
        
        logger.debug("Processing incoming request for {}.",  ctx.getRequest().getRequestURI());
        return null;
    }
    
    /**
     * Check the correlation ID is present or not.
     * 
     * @return  {@code true} if the correlation ID is present;
     *          {@code false} otherwise.
     */
    private boolean isCorrelationIdPresent() {
        return (filterUtils.getCorrelationId() != null);
    }
    
    /**
     * Check the access token is present or not.
     * 
     * @return  {@code true} if the access token is present;
     *          {@code false} otherwise.
     */
    private boolean isAccessTokenPresent() {
    	return (filterUtils.getAuthToken() != null);
    }

    /**
     * Generate a correlation ID by using GUIID value.
     * 
     * @return  The generated correlation ID.
     */
    private String generateCorrelationId() {
    	return java.util.UUID.randomUUID().toString();
    }
    
    /**
     * Get the organization Id from the JWT token.
     * 
     * <p>You can add a custom field into a JWT token. So this method is to 
     * parse the custom field (the organization Id field) from the JWT token 
     * as a header in a HTTP request.
     * 
     * @return  The value of the organization Id field 
     *          or {@code null} if the organization Id field is missing in JWT 
     *          token.
     */
    protected String getOrganizationId(){
        String result = "";
        
        if (filterUtils.getAuthToken() != null) {
            String authToken = filterUtils.getAuthToken().replace("Bearer ","");
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(serviceConfig.getJwtSigningKey().getBytes("UTF-8"))
                        .parseClaimsJws(authToken).getBody();
                result = (String) claims.get("organizationId");
            } catch (Exception e){
                logger.error("There is an error occurred when decrypting JWT token", e);
            }
        }
        return result;
    }
}