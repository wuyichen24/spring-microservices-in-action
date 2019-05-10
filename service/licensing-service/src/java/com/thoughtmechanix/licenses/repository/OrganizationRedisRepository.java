package com.thoughtmechanix.licenses.repository;

import com.thoughtmechanix.licenses.model.Organization;

/**
 * The interface for defining the basic operations with Redis.
 * 
 * @author  Wuyi Chen
 * @date    05/09/2019
 * @version 1.0
 * @since   1.0
 */
public interface OrganizationRedisRepository {
    /**
     * Add a new organization record to Redis.
     * 
     * @param  org
     *         The organization record needs to be added.
     */
    void saveOrganization(Organization org);
    
    /**
     * Update a organization record in Redis.
     * 
     * @param  org
     *         The organization record needs to be updated to.
     */
    void updateOrganization(Organization org);
    
    /**
     * Delete a organization record by the organization ID from Redis.
     * 
     * @param  orgId
     *         The organization ID for identifying the record.
     */
    void deleteOrganization(String orgId);
    
    /**
     * Search a organization record by the organization ID.
     * 
     * @param  orgId
     *         The organization ID for looking up.
     *         
     * @return  The matched organization record.
     */
    Organization findOrganization(String orgId);
}
