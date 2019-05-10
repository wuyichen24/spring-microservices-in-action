package com.thoughtmechanix.zuulsvr;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.thoughtmechanix.zuulsvr.utils.UserContextInterceptor;

/**
 * The bootstrap class for the zuul server.
 * 
 * @author  Wuyi Chen
 * @date    04/08/2019
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@EnableZuulProxy                           // Enables the service to be a Zuul server
public class ZuulServerApplication {
    @LoadBalanced                          // This annotation tells Spring Cloud to create a Ribbon backed RestTemplate class.
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public Sampler defaultSampler() {
        return new AlwaysSampler();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }
}

