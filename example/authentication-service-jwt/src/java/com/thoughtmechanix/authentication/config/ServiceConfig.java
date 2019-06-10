/*
 * Copyright 2019 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thoughtmechanix.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * The config class for authentication service.
 * 
 * <p>The fields in this class will map to the configuration file store in the 
 * config server: 
 * <ul>
 *   <li>config.authentication/authenticationservice.yml
 *   <li>config.authentication/authenticationservice-dev.yml
 *   <li>config.authentication/authenticationservice-prod.yml
 * </ul>
 * 
 * @author  Wuyi Chen
 * @date    06/06/2019
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
