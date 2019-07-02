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
package com.thoughtmechanix.authentication.security;

import com.thoughtmechanix.authentication.model.UserOrganization;
import com.thoughtmechanix.authentication.repository.OrgUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * The class to add more fields into the JWT token.
 * 
 * <p>After decrypting a JWT token, it will be JSON format. This class is to 
 * add organizationId field into the JWT token.
 * 
 * @author  Wuyi Chen
 * @date    04/09/2019
 * @version 1.0
 * @since   1.0
 */
public class JWTTokenEnhancer implements TokenEnhancer {
    @Autowired
    private OrgUserRepository orgUserRepo;

    /**
     * Get the organization Id by the user name.
     * 
     * @param  userName
     *         The user name needs to be searched.
     *         
     * @return  The organization Id.
     */
    private String getOrgId(String userName){
        UserOrganization orgUser = orgUserRepo.findByUserName( userName );
        return orgUser.getOrganizationId();
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        String              orgId          = getOrgId(authentication.getName());

        additionalInfo.put("organizationId", orgId);                        // Add organizationId field into JWT token

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
