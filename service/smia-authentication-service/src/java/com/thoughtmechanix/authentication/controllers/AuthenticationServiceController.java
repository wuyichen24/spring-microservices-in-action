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
package com.thoughtmechanix.authentication.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtmechanix.authentication.model.OauthClientDetails;
import com.thoughtmechanix.authentication.model.Users;
import com.thoughtmechanix.authentication.services.AuthenticationService;

/**
 * The controller class for defining available calls to the API endpoint of 
 * authentication service.
 * 
 * @author  Wuyi Chen
 * @date    06/28/2019
 * @version 1.0
 * @since   1.0
 */
@RestController
@RequestMapping             // authentication service is already defined the prefix: /auth
public class AuthenticationServiceController {
	@Autowired
	private AuthenticationService authService;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceController.class);
	
	/**
	 * Query a client by the client ID.
	 * 
	 * @param  clientId
	 *         The client ID for looking up.
	 *         
	 * @return  The matched client record.
	 */
	@RequestMapping(value="/client/{clientId}", method = RequestMethod.GET)
	public OauthClientDetails getClient(@PathVariable("clientId") String clientId) {
		logger.debug("Query a client by the client ID {}", clientId);
		return authService.getClient(clientId);
	}
	
	/**
	 * Add a new client.
	 * 
	 * @param  client
	 *         The new client needs to be added.
	 */
	@RequestMapping(value="/client", method = RequestMethod.POST) 
	public void saveClient(@RequestBody OauthClientDetails client) {
		logger.debug("Add a new client: {}", client.getClientId());
		authService.saveClient(client);
	}
	
	/**
	 * Update a client.
	 * 
	 * @param  client
	 *         The client needs to be updated to.
	 */
	@RequestMapping(value="/client", method = RequestMethod.PUT) 
	public void updateClient(@RequestBody OauthClientDetails client) {
		logger.debug("Update a client: {}", client.getClientId());
		authService.updateClient(client);
	}
	
	/**
	 * Delete a client by the client ID.
	 * 
	 * @param  clientId
	 *         The client ID for identifying the record needs to be deleted.
	 */
	@RequestMapping(value="/client/{clientId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCLient(@PathVariable("clientId") String clientId) {
		logger.debug("Delete a client by the client ID {}", clientId);
		authService.deleteClient(clientId);
	}
	
	/**
	 * Query a user by the username.
	 * 
	 * @param  username
	 *         The username for looking up.
	 *         
	 * @return  The matched user record.
	 */
	@RequestMapping(value="/user/{username:.+}", method = RequestMethod.GET)
	public Users getUser(@PathVariable("username") String username) {
		logger.debug("Query a user by the username {}", username);
		return authService.getUser(username);
	}
	
	/**
	 * Add a new user.
	 * 
	 * @param  user
	 *         The new user needs to be added.
	 */
	@RequestMapping(value="/user", method = RequestMethod.POST) 
	public void saveUser(@RequestBody Users user) {
		logger.debug("Add a new user: {}", user.getUsername());
		authService.saveUser(user);
	}
	
	/**
	 * Update a user.
	 * 
	 * @param  user
	 *         The client needs to be updated to.
	 */
	@RequestMapping(value="/user", method = RequestMethod.PUT)
	public void updateUser(@RequestBody Users user) {
		logger.debug("Update a user: {}", user.getUsername());
		authService.updateUser(user);
	}
	
	/**
	 * Delete a user by the username.
	 * 
	 * @param  username
	 *         The username for identifying the record needs to be deleted.
	 */
	@RequestMapping(value="/user/{username:.+}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("username") String username) {
		logger.debug("Delete a user by the username {}", username);
		authService.deleteUser(username);
	}
}
