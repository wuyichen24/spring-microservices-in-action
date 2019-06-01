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
package com.thoughtmechanix.zuulsvr.utils;

import org.springframework.stereotype.Component;

/**
 * The class to hold the HTTP header values.
 * 
 * @author  Wuyi Chen
 * @date    03/25/2019
 * @version 1.0
 * @since   1.0
 */
@Component
public class UserContext {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "Authorization";
    public static final String USER_ID        = "tmx-user-id";
    public static final String ORG_ID         = "tmx-org-id";

    private String correlationId = "";
    private String authToken     = "";
    private String userId        = "";
    private String orgId         = "";

    public String getCorrelationId()                     { return correlationId;               }
    public void   setCorrelationId(String correlationId) { this.correlationId = correlationId; }
    public String getAuthToken()                         { return authToken;                   }
    public void   setAuthToken(String authToken)         { this.authToken = authToken;         }
    public String getUserId()                            { return userId;                      }
    public void   setUserId(String userId)               { this.userId = userId;               }
    public String getOrgId()                             { return orgId;                       }
    public void   setOrgId(String orgId)                 { this.orgId = orgId;                 }
}