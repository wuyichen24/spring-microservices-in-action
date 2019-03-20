package com.thoughtmechanix.licenses.hystrix;

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
