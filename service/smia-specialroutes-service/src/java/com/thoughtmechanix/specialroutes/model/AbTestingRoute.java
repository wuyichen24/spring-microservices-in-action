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
package com.thoughtmechanix.specialroutes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The entity class for AB testing route.
 * 
 * @author  Wuyi Chen
 * @date    06/10/2019
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "abtesting")
public class AbTestingRoute {
    @Id
    @Column(name = "service_name", nullable = false)
    String serviceName;

    @Column(name="active", nullable = false)
    String active;

    @Column(name = "endpoint", nullable = false)
    String endpoint;

    @Column(name = "weight", nullable = false)
    Integer weight;

    public String  getActive()                        { return active;                  }
    public void    setActive(String active)           { this.active = active;           }
    public String  getServiceName()                   { return serviceName;             }
    public void    setServiceName(String serviceName) { this.serviceName = serviceName; }
    public String  getEndpoint()                      { return endpoint;                }
    public void    setEndpoint(String endpoint)       { this.endpoint = endpoint;       }
    public Integer getWeight()                        { return weight;                  }
    public void    setWeight(Integer weight)          { this.weight = weight;           }
}