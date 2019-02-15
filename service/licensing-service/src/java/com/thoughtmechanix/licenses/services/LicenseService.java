package com.thoughtmechanix.licenses.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
    private LicenseRepository licenseRepository;          // Autowired by com.thoughtmechanix.licenses.repository.LicenseRepositoryImpl

    @Autowired
    ServiceConfig config;

//    @Autowired
//    OrganizationRestTemplateClient organizationRestClient;

    public License getLicense(String organizationId,String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(config.getExampleProperty());
    }
    
    public List<License> getLicensesByOrg(String organizationId) {
//        randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }
    
    public void saveLicense(License license){
        license.withId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    @HystrixCommand
    private Organization getOrganization(String organizationId) {
//        return organizationRestClient.getOrganization(organizationId);
    		return null;
    }

    private void randomlyRunLong(){
    	Random rand = new Random();

    	int randomNum = rand.nextInt((3 - 1) + 1) + 1;

    	if (randomNum==3) {
    		sleep();
    	}
    }

    private void sleep(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @HystrixCommand
    (
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
    
    

    private List<License> buildFallbackLicenseList(String organizationId){
        List<License> fallbackList = new ArrayList<>();
        License license = new License()
                .withId("0000000-00-00000")
                .withOrganizationId( organizationId )
                .withProductName("Sorry no licensing information currently available");

        fallbackList.add(license);
        return fallbackList;
    }

   

    public void updateLicense(License license){
//    	licenseRepository.save(license);
    }

    public void deleteLicense(License license){
//        licenseRepository.delete( license.getLicenseId());
    }
}
