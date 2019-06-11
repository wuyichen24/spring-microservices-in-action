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
package com.thoughtmechanix.organization;

import com.thoughtmechanix.organization.utils.UserContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import javax.servlet.Filter;

/**
 * The bootstrap class for the organization service.
 * 
 * @author  Wuyi Chen
 * @date    06/10/2019
 * @version 1.0
 * @since   1.0
 */
@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer                           // This is the protected service by OAuth2
@EnableBinding(Source.class)
public class Application {
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
     * Get a {@code UserContextFilter}.
     * 
     * @return  The object of {@code UserContextFilter}.
     */
    @Bean
    public Filter userContextFilter() {
        UserContextFilter userContextFilter = new UserContextFilter();
        return userContextFilter;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
