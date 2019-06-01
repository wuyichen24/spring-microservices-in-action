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
