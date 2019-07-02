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
package com.thoughtmechanix.organization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The entity class for organization.
 * 
 * @author  Wuyi Chen
 * @date    02/14/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @Column(name = "organization_id", nullable = false)
    String id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "contact_name", nullable = false)
    String contactName;

    @Column(name = "contact_email", nullable = false)
    String contactEmail;

    @Column(name = "contact_phone", nullable = false)
    String contactPhone;

    public String getId()                              { return id;                        }
    public void   setId(String id)                     { this.id = id;                     }
    public String getName()                            { return name;                      }
    public void   setName(String name)                 { this.name = name;                 }
    public String getContactName()                     { return contactName;               }
    public void   setContactName(String contactName)   { this.contactName = contactName;   }
    public String getContactEmail()                    { return contactEmail;              }
    public void   setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public String getContactPhone()                    { return contactPhone;              }
    public void   setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
}
