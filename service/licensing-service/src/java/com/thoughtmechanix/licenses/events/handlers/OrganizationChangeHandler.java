package com.thoughtmechanix.licenses.events.handlers;

import com.thoughtmechanix.licenses.events.CustomChannels;
import com.thoughtmechanix.licenses.events.models.OrganizationChangeModel;
import com.thoughtmechanix.licenses.repository.OrganizationRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * The message handler for receiving the message of the organization change 
 * from the message queue.
 * 
 * @author  Wuyi Chen
 * @date    05/08/2019
 * @version 1.0
 * @since   1.0
 */
@EnableBinding(Sink.class)
public class OrganizationChangeHandler {
//    @Autowired
//    private OrganizationRedisRepository organizationRedisRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationChangeHandler.class);

    @StreamListener(Sink.INPUT)
    public void loggerSink(OrganizationChangeModel orgChange) {
    	logger.info("Received an event for organization id {}", orgChange.getOrganizationId());
//        
//        switch(orgChange.getAction()){
//            case "GET":
//                logger.debug("Received a GET event from the organization service for organization id {}", orgChange.getOrganizationId());
//                break;
//            case "SAVE":
//                logger.debug("Received a SAVE event from the organization service for organization id {}", orgChange.getOrganizationId());
//                break;
////            case "UPDATE":
////                logger.debug("Received a UPDATE event from the organization service for organization id {}", orgChange.getOrganizationId());
////                organizationRedisRepository.deleteOrganization(orgChange.getOrganizationId());
////                break;
////            case "DELETE":
////                logger.debug("Received a DELETE event from the organization service for organization id {}", orgChange.getOrganizationId());
////                organizationRedisRepository.deleteOrganization(orgChange.getOrganizationId());
////                break;
//            default:
//                logger.error("Received an UNKNOWN event from the organization service of type {}", orgChange.getType());
//                break;
//
//        }
    }
}
