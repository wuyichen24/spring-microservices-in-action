package com.thoughtmechanix.licenses.clients;

import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.repository.OrganizationRedisRepository;
import com.thoughtmechanix.licenses.utils.UserContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * The client for invoking the organization service and Redis server.
 *
 * @author  Wuyi Chen
 * @date    03/07/2019
 * @version 1.0
 * @since   1.0
 */
@Component
public class OrganizationRestTemplateClient {
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	OrganizationRedisRepository orgRedisRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

	/**
	 * Get a organization record by organization ID.
	 * 
	 * <p>The licensing service always check Redis first to see the matched 
	 * organization record already cached or not. If it has been cached, 
	 * retrieve the matched organization record for Redis directly. If not, 
	 * make a call to the organization service to search the organization 
	 * record and then cache it into Redis.
	 * 
	 * @param  organizationId
	 *         The organization ID for looking up.
	 *         
	 * @return  The matched organization record.
	 */
	public Organization getOrganization(String organizationId){
        logger.debug("In Licensing Service.getOrganization: {}", UserContextHolder.getContext().getCorrelationId());

        Organization org = checkRedisCache(organizationId);

        if (org != null){
            logger.debug("I have successfully retrieved an organization {} from the redis cache: {}", organizationId, org);
            return org;
        }

        logger.debug("Unable to locate organization from the redis cache: {}.", organizationId);
        
        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                        "http://localhost:5555/organizationservice/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        org = restExchange.getBody();

        if (org!=null) {
            cacheOrganizationObject(org);
        }

        return org;
    }
	
    /**
     * Check Redis has a certain organization record or not.
     * 
     * @param  organizationId
     *         The organization ID for looking up.
     *         
     * @return  The match organization record if found;
     *          Otherwise, return {@code null}.
     */
    private Organization checkRedisCache(String organizationId) {
        try {
            return orgRedisRepo.findOrganization(organizationId);
        } catch (Exception ex){
            logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.  Exception {}", organizationId, ex);
            return null;
        }
    }

    /**
     * Save a organization record into Redis cache.
     * 
     * @param  org
     *         The organization record needs to be saved.
     */
    private void cacheOrganizationObject(Organization org) {
        try {
            orgRedisRepo.saveOrganization(org);
        } catch (Exception ex){
            logger.error("Unable to cache organization {} in Redis. Exception {}", org.getId(), ex);
        }
    }
}