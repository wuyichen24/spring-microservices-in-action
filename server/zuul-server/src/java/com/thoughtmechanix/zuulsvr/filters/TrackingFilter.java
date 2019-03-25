package com.thoughtmechanix.zuulsvr.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
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

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;     // Type: Define the filter type is pre-filter
    }

    @Override
    public int filterOrder() {                  // Execution Order: Define the order of execution across multiple Filters
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;                   // Criteria: Define the filter will be invoked or not
    }

    @Override
    public Object run() {                       // Action: The action to be executed if the Criteria is met
        if (isCorrelationIdPresent()) {
           logger.debug("tmx-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId());
        } else{
            filterUtils.setCorrelationId(generateCorrelationId());
            logger.debug("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
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
     * Generate a correlation ID by using GUIID value.
     * 
     * @return  The generated correlation ID.
     */
    private String generateCorrelationId() {
    		return java.util.UUID.randomUUID().toString();
    }
}