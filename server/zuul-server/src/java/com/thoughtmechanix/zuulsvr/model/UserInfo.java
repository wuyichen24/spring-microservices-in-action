package com.thoughtmechanix.zuulsvr.model;

/**
 * The class to wrap the user information.
 * 
 * @author  Wuyi Chen
 * @date    03/03/2019
 * @version 1.0
 * @since   1.0
 */
public class UserInfo {
    String organizationId;
    String userId;

    public String getOrganizationId()                      { return this.organizationId;           }
    public void   setOrganizationId(String organizationId) { this.organizationId = organizationId; }
    public String getUserId()                              { return userId;                        }
    public void   setUserId(String userId)                 { this.userId = userId;                 }
}
