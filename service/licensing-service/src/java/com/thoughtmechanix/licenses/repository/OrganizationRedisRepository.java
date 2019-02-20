package com.thoughtmechanix.licenses.repository;

import com.thoughtmechanix.licenses.entity.Organization;

public interface OrganizationRedisRepository {
    void saveOrganization(Organization org);
    void updateOrganization(Organization org);
    void deleteOrganization(String organizationId);
    Organization findOrganization(String organizationId);
}
