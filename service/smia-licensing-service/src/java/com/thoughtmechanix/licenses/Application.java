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
package com.thoughtmechanix.licenses;

import java.util.Collections;
import java.util.List;

import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.repository.LicenseRepository;
import com.thoughtmechanix.licenses.utils.UserContextInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

/**
 * The bootstrap class for the licensing service.
 * 
 * @author  Wuyi Chen
 * @date    04/08/2019
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@EntityScan(basePackageClasses = {License.class})
@EnableJpaRepositories(basePackageClasses = {LicenseRepository.class})
@EnableDiscoveryClient       // Enable Spring DiscoveryClient (call services registered with service discovery engine)
@EnableFeignClients          // Enable Netflix Feign (call services registered with service discovery engine)
@EnableCircuitBreaker        // Enable Hystrix
@EnableEurekaClient
@RefreshScope
public class Application {
    @Autowired
    private ServiceConfig serviceConfig;

    /**
     * Replace the default Sampler by {@code AlwaysSampler}.
     * 
     * <p>The purpose of this function is to send all the tracing data to 
     * the Zipkin server, otherwise only 10% tracing data will be sent.
     * 
     * @return  The {@code AlwaysSampler} object to replace the default 
     *          Sampler.
     */
    @Bean
	public Sampler defaultSampler() {
		return new AlwaysSampler();
	}
    
    /**
     * Build the connection to Redis server.
     * 
     * @return  The object of {@code JedisConnectionFactory}.
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
        jedisConnFactory.setHostName(serviceConfig.getRedisServer());
        jedisConnFactory.setPort(serviceConfig.getRedisPort());
        return jedisConnFactory;
    }

    /**
     * Create a {@code RedisTemplate} object for executing operations with 
     * Redis.
     * 
     * @return  The object of {@code RedisTemplate}.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
    
    
    /**
     * Get a {@code RestTemplate}.
     * 
     * @return  The object of {@code RestTemplate}.
     */
    @LoadBalanced                          // This annotation tells Spring Cloud to create a Ribbon backed RestTemplate class.
    @Bean
    public RestTemplate getRestTemplate(){
    	return new RestTemplate();
    }
    
    /**
     * Inject the access token into the downstream service calls.
     * 
     * @return  The {@code RestTemplate} for sending HTTP requests.
     */
    @Primary
    @Bean
    public RestTemplate getCustomRestTemplate() {
    	RestTemplate template = new RestTemplate();
    	List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
    	if (interceptors == null) {
    		template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));  // UserContextInterceptor will inject Authentication header in every REST call
    	} else {
    		interceptors.add(new UserContextInterceptor());
    		template.setInterceptors(interceptors);
    	}
    	return template;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
