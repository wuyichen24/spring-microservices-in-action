package com.thoughtmechanix.licenses;

import java.util.Collections;
import java.util.List;

import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.utils.UserContextInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
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
@EntityScan(basePackages = {"com.thoughtmechanix.licenses.model"})
@EnableJpaRepositories(basePackages = {"com.thoughtmechanix.licenses.repository"})
@EnableDiscoveryClient       // Enable Spring DiscoveryClient (call services registered with service discovery engine)
@EnableFeignClients          // Enable Netflix Feign (call services registered with service discovery engine)
@EnableCircuitBreaker        // Enable Hystrix
@EnableEurekaClient
@RefreshScope
public class Application {
    @Autowired
    private ServiceConfig serviceConfig;
    
    private static final Logger   logger        = LoggerFactory.getLogger(Application.class);

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
