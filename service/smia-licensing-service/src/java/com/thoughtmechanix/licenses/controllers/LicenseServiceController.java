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
package com.thoughtmechanix.licenses.controllers;

import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.services.LicenseService;

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
import java.util.List;

/**
 * The controller class of licensing service.
 * 
 * @author  Wuyi Chen
 * @date    05/08/2019
 * @version 1.0
 * @since   1.0
 */
@RestController
@RequestMapping(value="v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
    @Autowired
    private LicenseService licenseService;

    private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);

    /**
     * Query a multiple licenses by the organization ID.
     * 
     * @param  orgId
     *         The organization ID for looking up.
     *         
     * @return  The all the matched license records.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<License> getLicenses(@PathVariable("organizationId") String orgId) {
    	logger.debug("Query a multiple licenses by the organization ID {}", orgId);
    	
        return licenseService.getLicensesByOrg(orgId);
    }

    /**
     * Query a license by the organization ID and the license ID.
     * 
     * @param  orgId
     *         The organization ID for looking up.
     *         
     * @param  licenseId
     *         The license ID for looking up.
     * 
     * @return  The matched license record.
     */
    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicenses(@PathVariable("organizationId") String orgId, @PathVariable("licenseId") String licenseId) {
    	logger.debug("Query a license by the organization ID and the license ID {} {}", orgId, licenseId);
    	
        return licenseService.getLicense(orgId, licenseId);
    }
    
    /**
     * Query a license by the organization ID, the license ID and the client type.
     * 
     * @param  orgId
     *         The organization ID for looking up.
     *         
     * @param  licenseId
     *         The license ID for looking up.
     *         
     * @param  clientType
     *         The client type for looking up.
     * 
     * @return  The matched license record.
     */
    @RequestMapping(value="/{licenseId}/{clientType}", method = RequestMethod.GET)
    public License getLicensesWithClient(@PathVariable("organizationId") String orgId, @PathVariable("licenseId") String licenseId, @PathVariable("clientType") String clientType) {
    	logger.debug("Query a license by the organization ID, the license ID and the client type {} {} {}", orgId, licenseId, clientType);
    	
    	return licenseService.getLicense(orgId, licenseId, clientType);
    }

    /**
     * Update a license by the license ID.
     * 
     * @param  licenseId
     *         The license ID for looking up.
     *         
     * @param  license
     *         The license information needs to be updated to.
     */
    @RequestMapping(value = "/{licenseId}", method = RequestMethod.PUT)
    public void updateLicenses(@PathVariable("licenseId") String licenseId, @RequestBody License license) {
        licenseService.updateLicense(license);
    }

    /**
     * Add a new license.
     * 
     * @param  license
     *         The new license needs to be added.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void saveLicenses(@RequestBody License license) {
        licenseService.saveLicense(license);
    }

    /**
     * Delete a license by the license ID.
     * 
     * @param  licenseId
     *         The license ID for identifying the record needs to be deleted.
     */
    @RequestMapping(value = "/{licenseId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLicenses(@PathVariable("licenseId") String licenseId) {
         licenseService.deleteLicense(licenseId);
    }
}
