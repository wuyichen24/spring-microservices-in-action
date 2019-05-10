package com.thoughtmechanix.licenses.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The config class for licensing service.
 * 
 * @author  Wuyi Chen
 * @date    05/09/2019
 * @version 1.0
 * @since   1.0
 */
@Component
public class ServiceConfig{
	@Value("${example.property}")
	private String exampleProperty="";

	@Value("${redis.server}")
	private String redisServer="";

	@Value("${redis.port}")
	private String redisPort="";

	public String getExampleProperty(){
		return exampleProperty;
	}

	public String getRedisServer(){
		return redisServer;
	}

	public Integer getRedisPort(){
		return new Integer(redisPort).intValue();
	}
}
