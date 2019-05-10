package com.thoughtmechanix.licenses.repository;

import com.thoughtmechanix.licenses.model.Organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * The concrete class for implementing the basic operations with Redis
 * 
 * @author  Wuyi Chen
 * @date    05/09/2019
 * @version 1.0
 * @since   1.0
 */
@Repository
public class OrganizationRedisRepositoryImpl implements OrganizationRedisRepository {
    private static final String HASH_NAME = "organization";

    private RedisTemplate<String, Object>          redisTemplate;
    private HashOperations<String, String, Object> hashOperations;

    /**
     * Construct a {@code OrganizationRedisRepositoryImpl}.
     */
    public OrganizationRedisRepositoryImpl(){
        super();
    }

    @Autowired
    private OrganizationRedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveOrganization(Organization org) {
        hashOperations.put(HASH_NAME, org.getId(), org);
    }

    @Override
    public void updateOrganization(Organization org) {
        hashOperations.put(HASH_NAME, org.getId(), org);
    }

    @Override
    public void deleteOrganization(String organizationId) {
        hashOperations.delete(HASH_NAME, organizationId);
    }

    @Override
    public Organization findOrganization(String organizationId) {
    	return (Organization) hashOperations.get(HASH_NAME, organizationId);
    }
}
