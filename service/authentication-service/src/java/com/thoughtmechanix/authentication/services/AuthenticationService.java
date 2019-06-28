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
package com.thoughtmechanix.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtmechanix.authentication.model.OauthClientDetails;
import com.thoughtmechanix.authentication.model.Users;
import com.thoughtmechanix.authentication.repository.OauthClientDetailsRepository;
import com.thoughtmechanix.authentication.repository.UsersRepository;
import com.thoughtmechanix.authentication.security.WebSecurityConfigurer;

/**
 * The authentication service for processing operations on clients and users.
 *
 * @author  Wuyi Chen
 * @date    06/26/2019
 * @version 1.0
 * @since   1.0
 */
@Service
public class AuthenticationService {
	private static final String MASK = "********";
	
	@Autowired
	private OauthClientDetailsRepository clientRepository;
	
	@Autowired
	private UsersRepository              userRepository;
	
	/**
	 * Query a client by the client ID.
	 * 
	 * <p>The client's secret will be masked.
	 * 
	 * @param  clientId
	 *         The client ID for looking up.
	 *         
	 * @return  The matched client record.
	 */
	public OauthClientDetails getClient(String clientId) {
		OauthClientDetails client = clientRepository.findByClientId(clientId);
		if (client != null) {
			client.setClientSecret(MASK);
		}
		return client;
	}
	
	/**
	 * Insert a new client.
	 * 
	 * @param  client
	 *         The new client needs to be inserted.
	 */
	public void saveClient(OauthClientDetails client) {
		clientRepository.save(client);
	}
	
	/**
	 * Update a client.
	 * 
	 * @param  client
	 *         The client needs to be updated to.
	 */
	public void updateClient(OauthClientDetails client) {
		clientRepository.save(client);
	}
	
	/**
	 * Delete a client by the client ID.
	 * 
	 * @param  clientId
	 *         The client ID for looking up.
	 */
	public void deleteClient(String clientId) {
		clientRepository.delete(clientId);
	}
	
	/**
	 * Query a user with authorities by the username.
	 * 
	 * @param  username
	 *         The username for looking up.
	 *         
	 * @return  The matched user record.
	 */
	public Users getUser(String username) {
		Users user = userRepository.findByUsername(username);
		if (user != null) {
			user.setPassword(MASK);
		}
		return user;
	}
	
	/**
	 * Insert a new user.
	 * 
	 * <p>The password will be encoded before inserting into the database.
	 * 
	 * @param  user
	 *         The new user needs to be inserted.
	 */
	public void saveUser(Users user) {
		user.setPassword(WebSecurityConfigurer.passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	/**
	 * Update a user.
	 * 
	 * <p>The password will be encoded before inserting into the database.
	 * 
	 * @param  user
	 *         The user needs to be updated to.
	 */
	public void updateUser(Users user) {
		user.setPassword(WebSecurityConfigurer.passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	/**
	 * Delete a user by the username.
	 * 
	 * @param  username
	 *         The username for looking up.
	 */
	public void deleteUser(String username) {
		userRepository.delete(username);
	}
}
