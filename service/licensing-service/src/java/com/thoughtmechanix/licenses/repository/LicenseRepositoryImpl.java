package com.thoughtmechanix.licenses.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.thoughtmechanix.licenses.model.License;

@Repository
public class LicenseRepositoryImpl implements LicenseRepository {
	@Override
	public <S extends License> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends License> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public License findOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<License> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<License> findAll(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(License entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends License> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<License> findByOrganizationId(String organizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public License findByOrganizationIdAndLicenseId(String organizationId, String licenseId) {
		// TODO Auto-generated method stub
		return null;
	}

}
