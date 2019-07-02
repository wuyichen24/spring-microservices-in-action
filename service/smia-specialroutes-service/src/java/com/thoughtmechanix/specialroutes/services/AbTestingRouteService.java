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
package com.thoughtmechanix.specialroutes.services;

import com.thoughtmechanix.specialroutes.exception.NoRouteFoundException;
import com.thoughtmechanix.specialroutes.model.AbTestingRoute;
import com.thoughtmechanix.specialroutes.repository.AbTestingRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The AB testing route service for processing operations on alternate routes.
 * 
 * @author  Wuyi Chen
 * @date    02/14/2019
 * @version 1.0
 * @since   1.0
 */
@Service
public class AbTestingRouteService {
    @Autowired
    private AbTestingRouteRepository abTestingRouteRepository;

    /**
     * Get an alternate route for a service.
     * 
     * @param  serviceName
     *         The name of the service.
     * 
     * @return  The alternate route for the service.
     */
    public AbTestingRoute getRoute(String serviceName) {
        AbTestingRoute route = abTestingRouteRepository.findByServiceName(serviceName);

        if (route == null){
            throw new NoRouteFoundException();
        }

        return route;
    }

    /**
     * Insert an alternate route for a service.
     * 
     * @param  route
     *         The alternate route record needs to be inserted.
     */
    public void saveAbTestingRoute(AbTestingRoute route){
        abTestingRouteRepository.save(route);
    }

    /**
     * Update an alternate route for a service.
     * 
     * @param  route
     *         The alternate route needs to be updated.
     */
    public void updateRouteAbTestingRoute(AbTestingRoute route){
        abTestingRouteRepository.save(route);
    }

    /**
     * Delete an alternate route for a service.
     * 
     * @param  route
     *         The alternate route needs to be deleted.
     */
    public void deleteRoute(AbTestingRoute route){
        abTestingRouteRepository.delete(route.getServiceName());
    }
}
