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
package com.thoughtmechanix.authentication.hystrix;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import com.thoughtmechanix.authentication.utils.UserContextHolder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The class to define a customized concurrency strategy.
 * 
 * @author  Wuyi Chen
 * @date    03/17/2019
 * @version 1.0
 * @since   1.0
 */
public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy{   // Extend the base HystrixConcurrencyStrategy Class
    private HystrixConcurrencyStrategy existingConcurrencyStrategy;

    /**
     * Construct a {@code ThreadLocalAwareStrategy}.
     * 
     * @param  existingConcurrencyStrategy
     *         The existing {@code HystrixConcurrencyStrategy} defined by 
     *         Spring Cloud.
     */
    public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy existingConcurrencyStrategy) {
        this.existingConcurrencyStrategy = existingConcurrencyStrategy;
    }

    @Override
    public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
        return existingConcurrencyStrategy != null
                ? existingConcurrencyStrategy.getBlockingQueue(maxQueueSize)   // Call the existing concurrency strategy's method
                : super.getBlockingQueue(maxQueueSize);
    }

    @Override
    public <T> HystrixRequestVariable<T> getRequestVariable(
            HystrixRequestVariableLifecycle<T> rv) {
        return existingConcurrencyStrategy != null
                ? existingConcurrencyStrategy.getRequestVariable(rv)           // Call the existing concurrency strategy's method
                : super.getRequestVariable(rv);
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
                                            HystrixProperty<Integer> corePoolSize,
                                            HystrixProperty<Integer> maximumPoolSize,
                                            HystrixProperty<Integer> keepAliveTime, TimeUnit unit,
                                            BlockingQueue<Runnable> workQueue) {
        return existingConcurrencyStrategy != null
                ? existingConcurrencyStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)
                : super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        return existingConcurrencyStrategy != null
                ? existingConcurrencyStrategy
                .wrapCallable(new DelegatingUserContextCallable<T>(callable, UserContextHolder.getContext()))    // Inject your Callable implementation that will set the UserContext
                : super.wrapCallable(new DelegatingUserContextCallable<T>(callable, UserContextHolder.getContext()));
    }
}