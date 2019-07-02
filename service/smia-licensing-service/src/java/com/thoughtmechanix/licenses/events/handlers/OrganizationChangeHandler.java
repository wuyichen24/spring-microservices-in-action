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
package com.thoughtmechanix.licenses.events.handlers;

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
 * <p>If the licensing service receives a message about a change on 
 * a organization record. the licensing service will delete the cached 
 * organization record (by organization ID) in Redis (because the record 
 * cached before is out-of-date).
 * 
 * @author  Wuyi Chen
 * @date    05/08/2019
 * @version 1.0
 * @since   1.0
 */
@EnableBinding(Sink.class)
public class OrganizationChangeHandler {
    @Autowired
    private OrganizationRedisRepository organizationRedisRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationChangeHandler.class);

    /**
     * Log the change event received from the organization service and update 
     * the Redis cache.
     * 
     * @param  orgChange
     *         The organization change event.
     */
    @StreamListener(Sink.INPUT)
    public void loggerSink(OrganizationChangeModel orgChange) {
    	logger.info("Received an event for organization id {}", orgChange.getOrganizationId());
    	        
        switch(orgChange.getAction()){
            case "GET":
                logger.debug("Received a GET event from the organization service for organization id {}", orgChange.getOrganizationId());
                break;
            case "SAVE":
                logger.debug("Received a SAVE event from the organization service for organization id {}", orgChange.getOrganizationId());
                break;
            case "UPDATE":
                logger.debug("Received a UPDATE event from the organization service for organization id {}", orgChange.getOrganizationId());
                organizationRedisRepository.deleteOrganization(orgChange.getOrganizationId());
                break;
            case "DELETE":
                logger.debug("Received a DELETE event from the organization service for organization id {}", orgChange.getOrganizationId());
                organizationRedisRepository.deleteOrganization(orgChange.getOrganizationId());
                break;
            default:
                logger.error("Received an UNKNOWN event from the organization service of type {}", orgChange.getType());
                break;
        }
    }
}
