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
package com.thoughtmechanix.specialroutes.controllers;

import com.thoughtmechanix.specialroutes.model.AbTestingRoute;
import com.thoughtmechanix.specialroutes.services.AbTestingRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * The controller class for defining available calls to the API endpoint of 
 * specialroutes service.
 * 
 * @author  Wuyi Chen
 * @date    06/10/2019
 * @version 1.0
 * @since   1.0
 */
@RestController
@RequestMapping(value="v1/route/")
public class SpecialRoutesServiceController {
    @Autowired
    AbTestingRouteService routeService;

    /**
     * Provide an alternate endpoint of a service.
     * 
     * @param  serviceName
     *         The name of the service.
     * 
     * @return  The alternate endpoint of the service.
     */
    @RequestMapping(value = "abtesting/{serviceName}", method = RequestMethod.GET)
    public AbTestingRoute abstestings(@PathVariable("serviceName") String serviceName) {
        return routeService.getRoute( serviceName);
    }
}
