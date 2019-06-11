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
package com.thoughtmechanix.licenses.events.models;

/**
 * The organization change event.
 * 
 * @author  Wuyi Chen
 * @date    05/05/2019
 * @version 1.0
 * @since   1.0
 */
public class OrganizationChangeModel{
    private String type;
    private String action;           // The action of change: Add, Update or Delete
    private String organizationId;   // The organization ID associated with the event
    private String correlationId;    // The correlation ID the service call triggered the event

    /**
     * Construct a {@code OrganizationChangeModel}.
     */
    public OrganizationChangeModel() {
    	super();
    }
    
    /**
     * Construct a {@code OrganizationChangeModel}.
     * 
     * @param  type
     *         The type of the change.
     *         
     * @param  action
     *         The action of the change: Add, Update or Delete
     * 
     * @param  organizationId
     *         The organization ID associated with the change event.
     *         
     * @param  correlationId
     *         The correlation ID the service call the triggered the event.
     */
    public OrganizationChangeModel(String type, String action, String organizationId, String correlationId) {
        super();
        this.type           = type;
        this.action         = action;
        this.organizationId = organizationId;
        this.correlationId  = correlationId;
    }

    public String getType()                                { return type;                          }
    public void   setType(String type)                     { this.type = type;                     }
    public String getAction()                              { return action;                        }
    public void   setAction(String action)                 { this.action = action;                 }
    public String getOrganizationId()                      { return organizationId;                }
    public void   setOrganizationId(String organizationId) { this.organizationId = organizationId; }
    public String getCorrelationId()                       { return correlationId;                 }
    public void   setCorrelationId(String correlationId)   { this.correlationId = correlationId;   }

    @Override
    public String toString() {
        return "OrganizationChangeModel [type=" + type +
                ", action=" + action +
                ", orgId="  + organizationId +
                ", correlationId=" + correlationId + "]";
    }
}
