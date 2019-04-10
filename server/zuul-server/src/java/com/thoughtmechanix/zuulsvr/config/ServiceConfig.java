package com.thoughtmechanix.zuulsvr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * The configuration class for Zuul server.
 * 
 * <p>The purpose of this class is mainly to load the JWT signing key from 
 * the application configuration file.
 * 
 * @author  Wuyi Chen
 * @date    04/10/2019
 * @version 1.0
 * @since   1.0
 */
@Component
@Configuration
public class ServiceConfig {
	@Value("${signing.key}")
	private String jwtSigningKey = "";

	public String getJwtSigningKey() {
		return jwtSigningKey;
	}
}
