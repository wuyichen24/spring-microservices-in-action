package com.thoughtmechanix.organization.events.source;

import com.thoughtmechanix.organization.event.models.OrganizationChangeModel;
import com.thoughtmechanix.organization.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * The class for publishing a message to the message broker.
 * 
 * <p>The published message is a change event which includes the organization 
 * ID and the action (Add, Update or Delete).
 * 
 * @author  Wuyi Chen
 * @date    05/05/2019
 * @version 1.0
 * @since   1.0
 */
@Component
public class SimpleSourceBean {
    private Source source;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    @Autowired
    public SimpleSourceBean(Source source) {                                   // Spring Cloud Stream will inject the implementation of Source interface to here
        this.source = source;
    }

    public void publishOrgChange(String action, String orgId){
       logger.debug("Sending Kafka message {} for Organization Id: {}", action, orgId);
        OrganizationChangeModel change =  new OrganizationChangeModel(         // This change object will be sent
                OrganizationChangeModel.class.getTypeName(),
                action,
                orgId,
                UserContext.getCorrelationId());           

        source.output().send(MessageBuilder.withPayload(change).build());      // Send message
    }
}
