package com.thoughtmechanix.licenses.utils;

import org.springframework.stereotype.Component;

/**
 * The class to hold the HTTP header values.
 * 
 * @author  Wuyi Chen
 * @date    03/14/2019
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