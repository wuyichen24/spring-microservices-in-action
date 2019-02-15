package com.thoughtmechanix.licenses.repository;

import com.thoughtmechanix.licenses.model.License;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

/**
 * The interface for customizing queries to license table
 * 
 * @author  Wuyi Chen
 * @date    02/14/2019
 * @version 1.0
 * @since   1.0
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String>  {
	/**
	 * Find a list of {@code License} records based on organization Id.
	 * 
	 * @param  organizationId
	 *         The organization Id needs to search for.
	 * 
	 * @return  The list of matched {@code License} records.
	 * 
     * @since   1.0
	 */
	public List<License> findByOrganizationId(String organizationId);
	
	/**
	 * Find a {@code License} records based on organization Id and license Id.
	 * 
	 * @param  organizationId
	 *         The organization Id needs to search for.
	 * 
	 * @param  licenseId
	 *         The license Id needs to search for.
	 * 
	 * @return  The match {@code License} record.
	 * 
     * @since   1.0
	 */
	public License       findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
