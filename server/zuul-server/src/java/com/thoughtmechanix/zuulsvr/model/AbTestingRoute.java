package com.thoughtmechanix.zuulsvr.model;

/**
 * The class to wrap the endpoint information.
 * 
 * @author  Wuyi Chen
 * @date    03/26/2019
 * @version 1.0
 * @since   1.0
 */
public class AbTestingRoute {
    String  serviceName;
    String  active;
    String  endpoint;
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