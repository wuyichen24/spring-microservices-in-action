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

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * The tool class for zuul filters.
 * 
 * <p>This class centralizes some common-used constants and operations.
 * 
 * @author  Wuyi Chen
 * @date    03/24/2019
 * @version 1.0
 * @since   1.0
 */
@Component
public class FilterUtils {
    public static final String CORRELATION_ID    = "tmx-correlation-id";
    public static final String AUTH_TOKEN        = "Authorization";
    public static final String USER_ID           = "tmx-user-id";
    public static final String ORG_ID            = "tmx-org-id";
    public static final String PRE_FILTER_TYPE   = "pre";
    public static final String POST_FILTER_TYPE  = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    /**
     * Get the correlation ID from the header of a HTTP request.
     * 
     * @return  The value of correlation ID.
     */
    public String getCorrelationId(){
        RequestContext ctx = RequestContext.getCurrentContext();

        if (ctx.getRequest().getHeader(CORRELATION_ID) !=null) {
            return ctx.getRequest().getHeader(CORRELATION_ID);
        } else {
            return ctx.getZuulRequestHeaders().get(CORRELATION_ID);
        }
    }

    /**
     * Set the correlation ID to the header of a HTTP request.
     * 
     * @param  correlationId
     *         The correlation ID needs to be set.
     */
    public void setCorrelationId(String correlationId){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }

    /**
     * Get the organization ID from the header of a HTTP request.
     * 
     * @return  The value of organization ID.
     */
    public  final String getOrgId(){
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(ORG_ID) !=null) {
            return ctx.getRequest().getHeader(ORG_ID);
        } else {
            return ctx.getZuulRequestHeaders().get(ORG_ID);
        }
    }

    /**
     * Set the organization ID to the header of a HTTP request.
     * 
     * @param  ordId
     *         The organization ID needs to be set.
     */
    public void setOrgId(String orgId){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(ORG_ID,  orgId);
    }

    /**
     * Get the user ID from the header of a HTTP request.
     * 
     * @return  The value of user ID.
     */
    public final String getUserId(){
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(USER_ID) !=null) {
            return ctx.getRequest().getHeader(USER_ID);
        } else {
            return ctx.getZuulRequestHeaders().get(USER_ID);
        }
    }

    /**
     * Set the user ID to the header of a HTTP request.
     * 
     * @param  userId
     *         The user ID needs to be set.
     */
    public void setUserId(String userId){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(USER_ID,  userId);
    }

    /**
     * Get the access token from the header of a HTTP request.
     * 
     * @return  The value of the authentication token.
     */
    public final String getAuthToken(){
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getRequest().getHeader(AUTH_TOKEN);
    }
    
    /**
     * Set the access token to the header of a HTTP request.
     * 
     * @param  authToken
     *         The access token needs to be set.
     */
    public void setAuthToken(String authToken){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(AUTH_TOKEN,  authToken);
    }

    /**
     * Get the service ID from the header of a HTTP request.
     * 
     * <p>If the service ID is missing in the header, this method will return 
     * an empty string.
     * 
     * @return  The value of service ID.
     */
    public String getServiceId(){
        RequestContext ctx = RequestContext.getCurrentContext();

        // We might not have a service id if we are using a static, non-eureka route.
        if (ctx.get("serviceId") == null) { 
        	return "";
        }
        return ctx.get("serviceId").toString();
    }
}
