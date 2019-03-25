package com.thoughtmechanix.licenses.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * The interceptor class to inject the correlation ID and the authentication 
 * token into any outgoing service call to downstream services.
 * 
 * <p>The basic logic is the {@code UserContextFilter} will get several 
 * parameters from the incoming HTTP header and store them into the 
 * {@code UserContext} object of the {@code UserContextHolder}. 
 * The {@code UserContextInterceptor} will inject the parameters from the 
 * {@code UserContextHolder} into the header of the outgoing service requests 
 * toward downstream services.
 * 
 * @author  Wuyi Chen
 * @date    03/24/2019
 * @version 1.0
 * @since   1.0
 */
public class UserContextInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());

        return execution.execute(request, body);
    }
}