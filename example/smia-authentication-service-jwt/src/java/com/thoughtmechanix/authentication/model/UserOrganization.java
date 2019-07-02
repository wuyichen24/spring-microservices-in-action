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
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

/**
 * The entity class for user organization.
 * 
 * @author  Wuyi Chen
 * @date    06/10/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "user_orgs")
public class UserOrganization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "organization_id", nullable = false)
    String organizationId;

    @Id
    @Column(name = "username", nullable = false)
    String userName;

    public String getUserName()                            { return userName;                      }
    public void   setUserName(String userName)             { this.userName = userName;             }
    public String getOrganizationId()                      { return organizationId;                }
    public void   setOrganizationId(String organizationId) { this.organizationId = organizationId; }
}
