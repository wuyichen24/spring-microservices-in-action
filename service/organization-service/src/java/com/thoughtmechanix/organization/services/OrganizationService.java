package com.thoughtmechanix.organization.services;

import com.thoughtmechanix.organization.events.source.SimpleSourceBean;
import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private OrganizationRepository orgRepository;                    // For database operations

    @Autowired
    private SimpleSourceBean       simpleSourceBean;                 // For publishing organization change event to the message queue

    /**
     * Query an organization by the organization ID.
     * 
     * @param  orgId
     *         The organization ID for looking up.
     *         
     * @return  The matched organization record.
     */
    public Organization getOrg(String orgId) {
        return orgRepository.findById(orgId);
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
