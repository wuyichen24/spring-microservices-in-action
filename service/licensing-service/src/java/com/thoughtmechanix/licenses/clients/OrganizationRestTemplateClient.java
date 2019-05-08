package com.thoughtmechanix.licenses.clients;

import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.repository.OrganizationRedisRepository;
import com.thoughtmechanix.licenses.utils.UserContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * The client for invoking the organization service by Ribbon-backed Spring RestTemplate
 *
 * @author  Wuyi Chen
 * @date    03/07/2019
 * @version 1.0
 * @since   1.0
 */
@Component
public class OrganizationRestTemplateClient {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	public Organization getOrganization(String organizationId) {
		ResponseEntity<Organization> restExchange = restTemplate.exchange(
				"http://organizationservice/v1/organizations/{organizationId}",    // The IP address of the instance you call is abstracted
				HttpMethod.GET,
				null, 
				Organization.class, 
				organizationId);
		
		return restExchange.getBody();
	}
	
//    @Autowired
//    OAuth2RestTemplate restTemplate;

//    @Autowired
//    OrganizationRedisRepository orgRedisRepo;

//    private Organization checkRedisCache(String organizationId) {
//        try {
//            return orgRedisRepo.findOrganization(organizationId);
//        } catch (Exception ex){
//            logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.  Exception {}", organizationId, ex);
//            return null;
//        }
//    }
//
//    private void cacheOrganizationObject(Organization org) {
//        try {
//            orgRedisRepo.saveOrganization(org);
//        } catch (Exception ex){
//            logger.error("Unable to cache organization {} in Redis. Exception {}", org.getId(), ex);
//        }
//    }

//    public Organization getOrganization(String organizationId){
//        logger.debug("In Licensing Service.getOrganization: {}", UserContext.getCorrelationId());
//
//        Organization org = checkRedisCache(organizationId);
//
//        if (org != null){
//            logger.debug("I have successfully retrieved an organization {} from the redis cache: {}", organizationId, org);
//            return org;
//        }
//
//        logger.debug("Unable to locate organization from the redis cache: {}.", organizationId);
//
////        ResponseEntity<Organization> restExchange = restTemplate.exchange(
////                        "http://zuulservice/api/organization/v1/organizations/{organizationId}",
////                        HttpMethod.GET,
////                        null, Organization.class, organizationId);
//
//        /*Save the record from cache*/
////        org = restExchange.getBody();
//
//        if (org!=null) {
//            cacheOrganizationObject(org);
//        }
//
//        return org;
//    		return null;
//    }
}