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
package com.thoughtmechanix.licenses.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * The discovery service for the eureka server's service discovery.
 * 
 * @author  Wuyi Chen
 * @date    02/14/2019
 * @version 1.0
 * @since   1.0
 */
@Service
public class DiscoveryService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * Get a list of the name of the services which is registered with eureka 
     * server.
     * 
     * @return  The list of the name of the services.
     */
    public List<String> getEurekaServices(){
    	List<String> services = new ArrayList<>();

        discoveryClient.getServices().forEach(serviceName -> {
            discoveryClient.getInstances(serviceName).forEach(instance-> {
                services.add( String.format("%s:%s",serviceName,instance.getUri()));
            });
        });

        return services;
    }
}
