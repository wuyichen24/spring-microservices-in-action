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
package com.thoughtmechanix.zuulsvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

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

    /**
     * Overload the default sampler.
     * 
     * <p>Overload the default sampler by the always sampler for sending all 
     * the trace data to Zipkin server.
     * 
     * @return The object of {@code AlwaysSampler}.
     */
    @Bean
    public Sampler defaultSampler() {
        return new AlwaysSampler();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }
}

