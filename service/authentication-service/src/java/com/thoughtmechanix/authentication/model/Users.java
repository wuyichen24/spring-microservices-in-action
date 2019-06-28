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
package com.thoughtmechanix.authentication.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The entity class for user.
 * 
 * @author  Wuyi Chen
 * @date    06/26/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "users")
public class Users {
	@Id
	@Column(name = "username", nullable = false)
	private String  username;
	
	@Column(name = "password", nullable = false)
	private String  password;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "username")
	private List<Authorities> authoritiesList;
	
	public String            getUsername()                                         { return username;                        }
	public void              setUsername(String username)                          { this.username = username;               }
	public String            getPassword()                                         { return password;                        }
	public void              setPassword(String password)                          { this.password = password;               }
	public boolean           isEnabled()                                           { return enabled;                         }
	public void              setEnabled(boolean enabled)                           { this.enabled = enabled;                 }
	public List<Authorities> getAuthoritiesList()                                  { return authoritiesList;                 }
	public void              setAuthoritiesList(List<Authorities> authoritiesList) { this.authoritiesList = authoritiesList; }
}
