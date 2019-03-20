package com.thoughtmechanix.licenses;

import com.thoughtmechanix.licenses.config.ServiceConfig;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

//@RefreshScope
//@EnableEurekaClient
//@EnableCircuitBreaker
//@EnableBinding(Sink.class)
//@EnableResourceServer

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
//
//    private static final Logger logger = LoggerFactory.getLogger(Application.class);
//
//    @Bean
//    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {
//        return new OAuth2RestTemplate(details, oauth2ClientContext);
//    }
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
//        jedisConnFactory.setHostName(serviceConfig.getRedisServer());
//        jedisConnFactory.setPort(serviceConfig.getRedisPort() );
//        return jedisConnFactory;
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        return template;
//    }
    
    @LoadBalanced                          // This annotation tells Spring Cloud to create a Ribbon backed RestTemplate class.
    @Bean
    public RestTemplate getRestTemplate(){
    	return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
