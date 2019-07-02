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

/**
 * The entity class for clients.
 * 
 * @author  Wuyi Chen
 * @date    06/26/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "oauth_client_details")
public class OauthClientDetails {
	@Id
    @Column(name = "client_id", nullable = false)
	private String clientId;
	
	@Column(name = "resource_ids")
	private String resourceIds;
	
	@Column(name = "client_secret")
	private String clientSecret;
	
	@Column(name = "scope")
	private String scope;
	
	@Column(name = "authorized_grant_types")
	private String authorizedGrantTypes;
	
	@Column(name = "web_server_redirect_uri")
	private String webServerRedirectUri;
	
	@Column(name = "authorities")
	private String authorities;
	
	@Column(name = "access_token_validity")
	private int    accessTokenValidity;
	
	@Column(name = "refresh_token_validity")
	private int    refreshTokenValidity;
	
	@Column(name = "additional_information")
	private String additionalInformation;
	
	@Column(name = "autoapprove")
	private String autoapprove;

	public String getClientId()                                          { return clientId;                                    }
	public void   setClientId(String clientId)                           { this.clientId = clientId;                           }
	public String getResourceIds()                                       { return resourceIds;                                 }
	public void   setResourceIds(String resourceIds)                     { this.resourceIds = resourceIds;                     }
	public String getClientSecret()                                      { return clientSecret;                                }
	public void   setClientSecret(String clientSecret)                   { this.clientSecret = clientSecret;                   }
	public String getScope()                                             { return scope;                                       }
	public void   setScope(String scope)                                 { this.scope = scope;                                 }
	public String getAuthorizedGrantTypes()                              { return authorizedGrantTypes;                        }
	public void   setAuthorizedGrantTypes(String authorizedGrantTypes)   { this.authorizedGrantTypes = authorizedGrantTypes;   }
	public String getWebServerRedirectUri()                              { return webServerRedirectUri;                        }
	public void   setWebServerRedirectUri(String webServerRedirectUri)   { this.webServerRedirectUri = webServerRedirectUri;   }
	public String getAuthorities()                                       { return authorities;                                 }
	public void   setAuthorities(String authorities)                     { this.authorities = authorities;                     }
	public int    getAccessTokenValidity()                               { return accessTokenValidity;                         }
	public void   setAccessTokenValidity(int accessTokenValidity)        { this.accessTokenValidity = accessTokenValidity;     }
	public int    getRefreshTokenValidity()                              { return refreshTokenValidity;                        }
	public void   setRefreshTokenValidity(int refreshTokenValidity)      { this.refreshTokenValidity = refreshTokenValidity;   }
	public String getAdditionalInformation()                             { return additionalInformation;                       }
	public void   setAdditionalInformation(String additionalInformation) { this.additionalInformation = additionalInformation; }
	public String getAutoapprove()                                       { return autoapprove;                                 }
	public void   setAutoapprove(String autoapprove)                     { this.autoapprove = autoapprove;                     }
}
