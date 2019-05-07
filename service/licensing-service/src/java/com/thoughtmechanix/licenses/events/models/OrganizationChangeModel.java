package com.thoughtmechanix.licenses.events.models;

/**
 * The event POJO for any organization change.
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
    private String correlationId;    // The correlation ID the service call the triggered the event

    public OrganizationChangeModel() {
    	super();
    }
    
    public OrganizationChangeModel(String type, String action, String organizationId, String correlationId) {
        super();
        this.type           = type;
        this.action         = action;
        this.organizationId = organizationId;
        this.correlationId  = correlationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }


    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public String toString() {
        return "OrganizationChangeModel [type=" + type +
                ", action=" + action +
                ", orgId="  + organizationId +
                ", correlationId=" + correlationId + "]";
    }
}
