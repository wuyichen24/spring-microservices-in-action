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
package com.thoughtmechanix.licenses.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The filter class to get some contextual information from the HTTP header of 
 * the REST call.
 * 
 * <p>This solution is the way to propagate the parameters in the HTTP header 
 * of the REST call to any downstream service calls.
 * 
 * <p>The basic logic is the {@code UserContextFilter} will get several 
 * parameters from the incoming HTTP header and store them into the 
 * {@code UserContext} object of the {@code UserContextHolder}. 
 * The {@code UserContextInterceptor} will inject the parameters from the 
 * {@code UserContextHolder} into the header of the outgoing service requests 
 * toward downstream services.
 * 
 * <p>This class will get following parameters from the HTTP header of the 
 * incoming REST call:
 * <ul>
 *   <li>CORRELATION_ID (tmx-correlation-id)
 *   <li>AUTH_TOKEN     (Authorization)
 *   <li>USER_ID        (tmx-user-id)
 *   <li>ORG_ID         (tmx-org-id)
 * </ul>
 *
 * @author  Wuyi Chen
 * @date    03/14/2019
 * @version 1.0
 * @since   1.0
 */
@Component
public class UserContextFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
        UserContextHolder.getContext().setUserId(       httpServletRequest.getHeader(UserContext.USER_ID));
        UserContextHolder.getContext().setAuthToken(    httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        UserContextHolder.getContext().setOrgId(        httpServletRequest.getHeader(UserContext.ORG_ID));

        logger.debug("UserContextFilter Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}