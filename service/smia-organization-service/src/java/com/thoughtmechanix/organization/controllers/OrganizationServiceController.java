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
package com.thoughtmechanix.organization.controllers;

import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.services.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The controller class for defining available calls to the API endpoint of 
 * organization service.
 * 
 * @author  Wuyi Chen
 * @date    05/08/2019
 * @version 1.0
 * @since   1.0
 */
@RestController
@RequestMapping(value="v1/organizations")
public class OrganizationServiceController {
    @Autowired
    private OrganizationService orgService;
    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceController.class);

    /**
     * Query an organization by the organization ID.
     * 
     * @param  orgId
     *         The organization ID for looking up.
     *         
     * @return  The matched organization record.
     */
    @RequestMapping(value="/{organizationId}", method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String orgId) {
        logger.debug("Query an organization by the organization ID {}", orgId);

        return orgService.getOrg(orgId);
    }
    
    /**
     * Add a new organization.
     * 
     * @param  org
     *         The new organization needs to be added.
     */
    @RequestMapping(value="/{organizationId}", method = RequestMethod.POST)
    public void saveOrganization(@RequestBody Organization org) {
    	logger.debug("Add a new organization: {}", org.getId());
    
    	orgService.saveOrg(org);
    }

    /**
     * Update an organization by the organization ID.
     * 
     * @param  orgId
     *         The organization ID for looking up.
     * 
     * @param  org
     *         The organization information needs to be updated to.
     */
    @RequestMapping(value="/{organizationId}", method = RequestMethod.PUT)
    public void updateOrganization(@PathVariable("organizationId") String orgId, @RequestBody Organization org) {
    	logger.debug("Update an organization by the organization ID {}", orgId);
    	
        orgService.updateOrg(org);
    }

    /**
     * Delete an organization by the organization ID.
     * 
     * @param  orgId
     *         The organization ID for identifying the record needs to be deleted.
     */
    @RequestMapping(value = "/{organizationId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("orgId") String orgId) {
    	logger.debug("Delete an organization by the organization ID {}", orgId);
    	
        orgService.deleteOrg(orgId);
    }
}
