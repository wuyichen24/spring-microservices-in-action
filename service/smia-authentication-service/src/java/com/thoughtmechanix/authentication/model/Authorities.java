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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The entity class for authorities.
 * 
 * @author  Wuyi Chen
 * @date    06/26/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "authorities")
public class Authorities {
	@Id
    @GeneratedValue
    private Long id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "authority", nullable = false)
	private String authority;

	public String getUsername()                  { return username;            }
	public void   setUsername(String username)   { this.username = username;   }
	public String getAuthority()                 { return authority;           }
	public void   setAuthority(String authority) { this.authority = authority; }
}
