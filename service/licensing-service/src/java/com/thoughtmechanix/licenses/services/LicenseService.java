package com.thoughtmechanix.licenses.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.licenses.clients.OrganizationDiscoveryClient;
import com.thoughtmechanix.licenses.clients.OrganizationFeignClient;
import com.thoughtmechanix.licenses.clients.OrganizationRestTemplateClient;
import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.repository.LicenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * The implementation of the license service
 *
 * @author  Wuyi Chen
 * @date    02/14/2019
 * @version 1.0
 * @since   1.0
 */
@Service
public class LicenseService {
    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    ServiceConfig config;
    
    @Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;

    public License getLicense(String organizationId,String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(config.getExampleProperty());
    }
    
    public License getLicense(String organizationId, String licenseId, String clientType) {
    	License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
    	
    	Organization org = retrieveOrgInfo(organizationId, clientType);
    	
    	return license
    		.withOrganizationName( org.getName())
    		.withContactName( org.getContactName())
    		.withContactEmail( org.getContactEmail() )
    		.withContactPhone( org.getContactPhone() )
    		.withComment(config.getExampleProperty());
    }
       
    @HystrixCommand (fallbackMethod = "buildFallbackLicenseList",           // if this function excess the timeout, it will trigger the fallback function
            threadPoolKey = "licenseByOrgThreadPool",                       // define the unique name of the new thread pool
            threadPoolProperties =
                    { @HystrixProperty(name = "coreSize",value="30"),       // the size of the new thread pool
                      @HystrixProperty(name="maxQueueSize", value="10")}    // the size of the queue in front of the thread pool 
    )                                                                       // to hold the requests when the thread pool is full
    public List<License> getLicensesByOrg(String organizationId) {
        randomlyRunLong();     // this function just simulate the long running randomly to trigger the time out exception from circuit breaker
        return licenseRepository.findByOrganizationId(organizationId);
    }
    
    public void saveLicense(License license){
        license.withId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    @HystrixCommand (
    		fallbackMethod = "buildFallbackLicenseList",
    		threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties =
                    {@HystrixProperty(name = "coreSize",value="30"),
                     @HystrixProperty(name="maxQueueSize", value="10")},
            commandProperties={         
                     @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
                     @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
                     @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                     @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
                     @HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")}
    )
    public void updateLicense(License license){
//    	licenseRepository.save(license);
    }

    public void deleteLicense(License license){
//        licenseRepository.delete( license.getLicenseId());
    }
    
    @HystrixCommand
    private Organization getOrganization(String organizationId) {
//        return organizationRestClient.getOrganization(organizationId);
    		return null;
    }

    /**
     * Simulate the long running randomly.
     * 
     * <p>This method is to simulate the long running on approximately every 
     * one in three calls. The long running will take a little over a second, 
     * and it will be longer than the default time out of the circuit breaker, 
     * so that the circuit breaker will be triggered.
     */
    private void randomlyRunLong() {
    		Random rand = new Random();

    		int randomNum = rand.nextInt((3 - 1) + 1) + 1;

    		if (randomNum == 3) {
    			sleep();
    		}
    }

    /**
     * Sleep 2 seconds.
     */
    private void sleep(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Look up different types of clients to invoke the organization service
     * 
     * <p>There are 3 types of clients:
     * <ul>
     *  <li>discovery: Spring DiscoveryClient with standard Spring RestTemplate.
     *  <li>rest: Ribbon-backed Spring RestTemplate.
     *  <li>feign: Netflix Feign via Ribbon.
     * </ul>
     * 
     * @param organizationId
     * @param clientType
     * @return
     */
    private Organization retrieveOrgInfo(String organizationId, String clientType){
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }
    
    /**
     * Build a fallback license list.
     * 
     * <p>This is the fallback function and it will be triggered if there is 
     * an error occurred when getting the license list. This is an alternative 
     * solution by returning a hard-coded value.
     * 
     * @param organizationId
     * @return
     */
    private List<License> buildFallbackLicenseList(String organizationId){
        List<License> fallbackList = new ArrayList<>();
        License license = new License()
                .withId("0000000-00-00000")
                .withOrganizationId( organizationId )
                .withProductName("Sorry no licensing information currently available");

        fallbackList.add(license);
        return fallbackList;
    }
}
