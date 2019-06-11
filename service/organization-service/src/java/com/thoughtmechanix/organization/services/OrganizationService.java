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
package com.thoughtmechanix.organization.services;

import com.thoughtmechanix.organization.events.source.SimpleSourceBean;
import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The organization service for processing operations on organization.
 *
 * @author  Wuyi Chen
 * @date    02/14/2019
 * @version 1.0
 * @since   1.0
 */
@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository orgRepository;                    // for database operations

    @Autowired
    private Tracer                 tracer;                           // for sending custom span to zipkin server
    
    @Autowired
    private SimpleSourceBean       simpleSourceBean;                 // for publishing organization change event to the message queue

    /**
     * Query an organization by the organization ID.
     * 
     * @param  orgId
     *         The organization ID for looking up.
     *         
     * @return  The matched organization record.
     */
    public Organization getOrg(String orgId) {
    	Span newSpan = tracer.createSpan("getOrgDBCall");            // create a new span for send custom span to zipkin server
    	
    	try {
    		return orgRepository.findById(orgId);
    	} finally {
    		newSpan.tag("peer.service", "mysql");
    		newSpan.logEvent(Span.CLIENT_RECV);
    		tracer.close(newSpan);
    	}
    }

    /**
     * Insert a new organization.
     * 
     * @param  org
     *         The new organization needs to be inserted.
     */
    public void saveOrg(Organization org){
        org.setId(UUID.randomUUID().toString());

        orgRepository.save(org);
        simpleSourceBean.publishOrgChange("SAVE", org.getId());      // When changing the organization data, send a message to the message queue
    }

    /**
     * Update an organization.
     * 
     * @param  org
     *         The organization needs to be updated to.
     */
    public void updateOrg(Organization org){
        orgRepository.save(org);
        simpleSourceBean.publishOrgChange("UPDATE", org.getId());    // When changing the organization data, send a message to the message queue

    }

    /**
     * Delete an organization by the organization ID.
     * 
     * @param  orgId
     *         The organization ID for looking up.
     */
    public void deleteOrg(String orgId){
        orgRepository.delete(orgId);
        simpleSourceBean.publishOrgChange("DELETE", orgId);          // When changing the organization data, send a message to the message queue
    }
}
