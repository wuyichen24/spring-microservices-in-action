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
import com.thoughtmechanix.zuulsvr.model.AbTestingRoute;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The route-filter class to route the service calls to an alternate endpoint 
 * based on service ID.
 * 
 * <p>This filter will take the following actions:
 * <ul>
 *   <li>Take the Eureka service ID of the service being called by Zuul and 
 *   call the SpecialRoutes service to see if there is any alternate endpoint 
 *   for the service being called by Zuul. If there is an alternate endpoint, 
 *   it will return the alternate endpoint address and the percentage of 
 *   calls (weight number) to be sent to new versus old service.
 *   <li>Determine that the request needs to be routed to that new alternate 
 *   endpoint or not. If the randomly generated number is under the weight 
 *   number of the alternate endpoint, this filter will send the request to 
 *   the new alternate endpoint.
 * </ul>
 * 
 * @author  Wuyi Chen
 * @date    03/26/2019
 * @version 1.0
 * @since   1.0
 */
@Component
public class SpecialRoutesFilter extends ZuulFilter {
    private static final int     FILTER_ORDER  = 1;
    private static final boolean SHOULD_FILTER = false;
    private static final Logger  logger        = LoggerFactory.getLogger(SpecialRoutesFilter.class);
    
    private ProxyRequestHelper helper = new ProxyRequestHelper();

    @Autowired
    FilterUtils filterUtils;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String filterType() {
        return FilterUtils.ROUTE_FILTER_TYPE;    // Type: Define the filter type is route-filter
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;                     // Execution Order: Define the order of execution across multiple Filters
    }

    @Override
    public boolean shouldFilter() {              // Criteria: Define the filter will be invoked or not
        return SHOULD_FILTER;
    }
    
    @Override
    public Object run() {                        // Action: The action to be executed if the Criteria is met
        RequestContext ctx = RequestContext.getCurrentContext();

        AbTestingRoute abTestRoute = getAbRoutingInfo(filterUtils.getServiceId());   // Check the SpecialRoutes service to see is there any alternate endpoint for that service ID

        if (abTestRoute != null && useSpecialRoute(abTestRoute)) {                   // Determine that the request needs to be routed to that new alternate endpoint or not.
            String route = buildRouteString(ctx.getRequest().getRequestURI(), abTestRoute.getEndpoint(), ctx.get("serviceId").toString());
            forwardToSpecialRoute(route);
        }

        return null;
    }

    /**
     * Call the SpecialRoutes service to see if there is any alternate 
     * endpoint for the service being called by Zuul.
     * 
     * @param  serviceName
     *         The Eureka service ID for the service being called by Zuul.
     *         
     * @return  The {@code AbTestingRoute} object which contains the alternate 
     *          end-point address and the percentage of calls (weight number) 
     *          to be sent to new versus old service.
     */
    private AbTestingRoute getAbRoutingInfo(String serviceName){
        ResponseEntity<AbTestingRoute> restExchange = null;
        try {
            restExchange = restTemplate.exchange("http://specialroutesservice/v1/route/abtesting/{serviceName}", HttpMethod.GET, null, AbTestingRoute.class, serviceName);
        } catch(HttpClientErrorException ex){
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
            		return null;
            }
            throw ex;
        }
        return restExchange.getBody();
    }
    
    /**
     * Determine the request needs to be routed to the new alternate endpoint 
     * or not.
     * 
     * <p>If the randomly generated number is under the weight number of 
     * the alternate endpoint, this filter will send the request to the new 
     * alternate endpoint.
     * 
     * @param  testRoute
     *         The {@code AbTestingRoute} object which contains the alternate 
     *         end-point address and the percentage of calls (weight number) 
     *         to be sent to new versus old service.
     *         
     * @return  {@code true} if the request needs to be routed to the new 
     *                       alternate endpoint;
     *          {@code false} otherwise.
     */
    public boolean useSpecialRoute(AbTestingRoute testRoute) {
        if (testRoute.getActive().equals("N")) { 
        	return false; 
        }

        Random random = new Random();
        int value = random.nextInt((10 - 1) + 1) + 1;    // Generate a random number between 1 and 10
        
        return  (testRoute.getWeight() < value);
    }

    /**
     * Build the route string to the new endpoint.
     * 
     * <p>This method will use the service name to divide the old route string 
     * into 2 parts: the old endpoint and the extra path (stripped route). 
     * Then replace the old endpoint by the new endpoint and construct the new 
     * route string by merging the new endpoint and the extra path.
     * 
     * <pre>
     *     old route: http://{old endpoint}/{service name}/{extra path}
     *     new route: http://{new endpoint}/{service name}/{extra path}
     * </pre>
     * 
     * @param  oldRouteString
     *         The whole URL to the old endpoint with the extra path.
     *         
     * @param  newEndpoint
     *         The new endpoint
     *         
     * @param  serviceName
     *         The name of the service being called by Zuul.
     *         
     * @return  The new route string.
     */
    private String buildRouteString(String oldRouteString, String newEndpoint, String serviceName){
        int index = oldRouteString.indexOf(serviceName);

        String strippedRoute = oldRouteString.substring(index + serviceName.length());
        String newRoute      = String.format("%s/%s", newEndpoint, strippedRoute);
        logger.info("Target route: {}", newRoute);
        return newRoute;
    }
    
    /**
     * Send the service call to the new alternate endpoint.
     * 
     * <p>This method will construct a new HTTP request ({@code HttpRequest}) 
     * by copying the headers, URL parameters, method and body of the original 
     * HTTP request ({@code HttpServletRequest}).
     * 
     * @param  route
     *         The whole route to new alternate endpoint.
     */
    private void forwardToSpecialRoute(String route) {
        RequestContext     context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        
        HttpRequest        newRequest = copyHttpRequest(request, route);                // Copy and construct a new request
        
        if (request.getContentLength() < 0) {
            context.setChunkedRequestBody();
        }
        this.helper.addIgnoredHeaders();
        
        CloseableHttpClient httpClient = null;

        try {
            httpClient              = HttpClients.createDefault();
            HttpHost     httpHost   = getHttpHost(new URL(route));
            HttpResponse response   = forwardRequest(httpClient, httpHost, newRequest);
            setResponse(response);
        } catch (Exception ex ) {
            logger.error("Error occurred when forwarding the request to " + route, ex);
        } finally{
            try {
            	if (httpClient!= null) {
            		httpClient.close();
            	}
            } catch(IOException ex) {
            	logger.error("Error occurred when closing the http request", ex);
            }
        }
    }
    
    /**
     * Construct the {@code HttpHost} object from the {@code URL} object.
     * 
     * @param  url
     *         The {@code URL} object.
     *         
     * @return  The constructed {@code HttpHost} object.
     */
    private HttpHost getHttpHost(URL url) {
        return new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
    }
    
    /**
     * Copy the existing HTTP request ({@code HttpServletRequest}) to a new 
     * HTTP request ({@code HttpRequest}).
     * 
     * @param  oldRequest
     *         The old HTTP request ({@code HttpServletRequest} object)
     * 
     * @param  uri
     *         The URI string.
     *         
     * @return  The new HTTP request ({@code HttpRequest} object)
     */
    private HttpRequest copyHttpRequest(HttpServletRequest oldRequest, String uri) {
    	// Copy the headers, URL parameters, method and body of the original HTTP request
    	MultiValueMap<String, String> headers = this.helper.buildZuulRequestHeaders(oldRequest);
        MultiValueMap<String, String> params  = this.helper.buildZuulRequestQueryParams(oldRequest);
        String                        method  = getMethod(oldRequest);
        InputStream                   body    = getRequestBody(oldRequest);
        
        // Construct a new HTTP request from pieces
        HttpRequest newRequest;
        int contentLength = oldRequest.getContentLength();
        InputStreamEntity entity = new InputStreamEntity(body, contentLength, oldRequest.getContentType() != null ? ContentType.create(oldRequest.getContentType()) : null);
        switch (method.toUpperCase()) {
        	case "POST":
        		HttpPost httpPost = new HttpPost(uri);
        		newRequest = httpPost;
        		httpPost.setEntity(entity);
        		break;
        	case "PUT":
        		HttpPut httpPut = new HttpPut(uri);
        		newRequest = httpPut;
        		httpPut.setEntity(entity);
        		break;
        	case "PATCH":
        		HttpPatch httpPatch = new HttpPatch(uri);
        		newRequest = httpPatch;
        		httpPatch.setEntity(entity);
        		break;
        	default:
        		newRequest = new BasicHttpRequest(method, uri);
        }
        newRequest.setHeaders(convertHeaders(headers));
        
        return newRequest;
    }

    /**
     * Get the method (GET, POST, etc.) of the HTTP request.
     * 
     * @param  request
     *         The HTTP request.
     *         
     * @return  The method string of the HTTP request.
     */
    private String getMethod(HttpServletRequest request) {
        return request.getMethod().toUpperCase();
    }
    
    /**
     * Get the body of the HTTP request.
     * 
     * @param  request
     *         The HTTP request.
     *         
     * @return  The body ({@code InputStream} object) of the HTTP request.
     */
    private InputStream getRequestBody(HttpServletRequest request) {
        InputStream requestEntity = null;
        try {
            requestEntity = request.getInputStream();
        } catch (IOException ex) {
            // it is ok if there is no request body
        }
        return requestEntity;
    }

    /**
     * Send a HTTP request to an endpoint.
     * 
     * @param  httpclient
     *         The {@code HttpClient} object.
     *         
     * @param  httpHost
     *         The {@code HttpHost} object representing the endpoint.
     * 
     * @param  httpRequest
     *         The {@code HttpRequest} object needs to be sent.
     * 
     * @return  The response of the request.
     * 
     * @throws  IOException
     *          If there is an error occurred when sending the request.
     */
    private HttpResponse forwardRequest(HttpClient httpclient, HttpHost httpHost, HttpRequest httpRequest) throws IOException {
        return httpclient.execute(httpHost, httpRequest);
    }

    /**
     * Set the response of Zuul server by the pieces of the response from 
     * the downstream service.
     * 
     * @param  response
     *         The response from the downstream service.
     *         
     * @throws  IOException
     *          If there is an error occurred when setting the response for 
     *          the Zuul server.
     */
    private void setResponse(HttpResponse response) throws IOException {
        this.helper.setResponse(response.getStatusLine().getStatusCode(), response.getEntity() == null ? 
        		null : response.getEntity().getContent(), revertHeaders(response.getAllHeaders()));
    }
    
    /**
     * Convert a {@code MultiValueMap} object to an array of 
     * the {@code Header} objects for the HTTP headers.
     * 
     * @param  headers
     *         The {@code MultiValueMap} object for the headers.
     * 
     * @return  The array of {@code Header} objects.
     */
    private Header[] convertHeaders(MultiValueMap<String, String> headers) {
        List<Header> list = new ArrayList<>();
        for (String name : headers.keySet()) {
            for (String value : headers.get(name)) {
                list.add(new BasicHeader(name, value));
            }
        }
        return list.toArray(new BasicHeader[0]);
    }

    /**
     * Convert an array of the {@code Header} objects to 
     * a {@code MultiValueMap} object for the HTTP header.
     * 
     * <p>This is a opposite operation for 
     * the {@link #convertHeaders(headers)} method.
     * 
     * @param  headers
     *         The array of the {@code Header} objects.
     * 
     * @return  The {@code MultiValueMap} object representing the headers.
     */
    private MultiValueMap<String, String> revertHeaders(Header[] headers) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        for (Header header : headers) {
            String name = header.getName();
            if (!map.containsKey(name)) {
                map.put(name, new ArrayList<String>());
            }
            map.get(name).add(header.getValue());
        }
        return map;
    }
}
