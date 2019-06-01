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

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The post-filter class to inject the correlation ID back into the header of 
 * the HTTP response.
 * 
 * @author  Wuyi Chen
 * @date    03/25/2019
 * @version 1.0
 * @since   1.0
 */
@Component
public class ResponseFilter extends ZuulFilter{
    private static final int      FILTER_ORDER  = 1;
    private static final boolean  SHOULD_FILTER = true;
    private static final Logger   logger        = LoggerFactory.getLogger(ResponseFilter.class);

    @Autowired
    FilterUtils filterUtils;

    @Override
    public String filterType() {
        return  FilterUtils.POST_FILTER_TYPE;    // Type: Define the filter type is post-filter
    }

    @Override
    public int filterOrder() {                   // Execution Order: Define the order of execution across multiple Filters
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {              // Criteria: Define the filter will be invoked or not
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {                        // Action: The action to be executed if the Criteria is met
        RequestContext ctx = RequestContext.getCurrentContext();

        logger.debug("Adding the correlation id to the outbound headers. {}", filterUtils.getCorrelationId());
        ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());    // Grab the correlation ID that was passed in on the original HTTP request and inject it to the response
        logger.debug("Completing outgoing request for {}.", ctx.getRequest().getRequestURI());

        return null;
    }
}
