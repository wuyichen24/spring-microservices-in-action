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
package com.thoughtmechanix.licenses.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The entity class for license.
 * 
 * @author  Wuyi Chen
 * @date    02/14/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "licenses")
public class License{
	@Id
	@Column(name = "license_id", nullable = false)
	private String licenseId;

	@Column(name = "organization_id", nullable = false)
	private String organizationId;

	@Transient
	private String organizationName ="";

	@Transient
	private String contactName ="";

	@Transient
	private String contactPhone ="";

	@Transient
	private String contactEmail ="";

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "license_type", nullable = false)
	private String licenseType;

	@Column(name = "license_max", nullable = false)
	private Integer licenseMax;

	@Column(name = "license_allocated", nullable = false)
	private Integer licenseAllocated;

	@Column(name = "comment")
	private String comment;

	public Integer getLicenseMax()                               { return licenseMax;                        }
	public void    setLicenseMax(Integer licenseMax)             { this.licenseMax = licenseMax;             }
	public Integer getLicenseAllocated()                         { return licenseAllocated;                  }
	public void    setLicenseAllocated(Integer licenseAllocated) { this.licenseAllocated = licenseAllocated; }
	public String  getLicenseId()                                { return licenseId;                         }
	public void    setLicenseId(String licenseId)                { this.licenseId = licenseId;               }
	public String  getOrganizationId()                           { return organizationId;                    }
	public void    setOrganizationId(String organizationId)      { this.organizationId = organizationId;     }
	public String  getProductName()                              { return productName;                       }
	public void    setProductName(String productName)            { this.productName = productName;           }
	public String  getLicenseType()                              { return licenseType;                       }
	public void    setLicenseType(String licenseType)            { this.licenseType = licenseType;           }
	public String  getComment()                                  { return comment;                           }
	public void    setComment(String comment)                    { this.comment = comment;                   }
	public String  getOrganizationName()                         { return organizationName;                  }
	public void    setOrganizationName(String organizationName)  { this.organizationName = organizationName; }
	public String  getContactName()                              { return contactName;                       }
	public void    setContactName(String contactName)            { this.contactName = contactName;           }
	public String  getContactPhone()                             { return contactPhone;                      }
	public void    setContactPhone(String contactPhone)          { this.contactPhone = contactPhone;         }
	public String  getContactEmail()                             { return contactEmail;                      }
	public void    setContactEmail(String contactEmail)          { this.contactEmail = contactEmail;         }

	public License withId(String id)                              { this.setLicenseId(id);                      return this; }
	public License withOrganizationId(String organizationId)      { this.setOrganizationId(organizationId);     return this; }
	public License withProductName(String productName)            { this.setProductName(productName);           return this; }
	public License withLicenseType(String licenseType)            { this.setLicenseType(licenseType);           return this; }
	public License withLicenseMax(Integer licenseMax)             { this.setLicenseMax(licenseMax);             return this; }
	public License withLicenseAllocated(Integer licenseAllocated) { this.setLicenseAllocated(licenseAllocated); return this; }
	public License withComment(String comment)                    { this.setComment(comment);                   return this; }
	public License withOrganizationName(String organizationName)  { this.setOrganizationName(organizationName); return this; }
	public License withContactName(String contactName)            { this.setContactName(contactName);           return this; }
	public License withContactPhone(String contactPhone)          { this.setContactPhone(contactPhone);         return this; }
	public License withContactEmail(String contactEmail)          { this.setContactEmail(contactEmail);         return this; }
}
