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
package com.thoughtmechanix.specialroutes.hystrix;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * The configuration class to let Spring Cloud use your customized Hystrix 
 * concurrency strategy ({@code ThreadLocalAwareStrategy}).
 * 
 * @author  Wuyi Chen
 * @date    03/17/2019
 * @version 1.0
 * @since   1.0
 */
@Configuration
public class ThreadLocalConfiguration {
	@Autowired(required = false)
	private HystrixConcurrencyStrategy existingConcurrencyStrategy;     // Autowire the existing HystrixConcurrencyStrategy

	@PostConstruct
	public void init() {
		// Keeps references of existing Hystrix plugins.
		HystrixEventNotifier        eventNotifier        = HystrixPlugins.getInstance().getEventNotifier();
        HystrixMetricsPublisher     metricsPublisher     = HystrixPlugins.getInstance().getMetricsPublisher();
        HystrixPropertiesStrategy   propertiesStrategy   = HystrixPlugins.getInstance().getPropertiesStrategy();
        HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();

        HystrixPlugins.reset();

        HystrixPlugins.getInstance().registerConcurrencyStrategy(new ThreadLocalAwareStrategy(existingConcurrencyStrategy));  // Register your customized strategy ({@code ThreadLocalAwareStrategy})
        HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
        HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
        HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
        HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
	}
}
