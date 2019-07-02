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

import com.thoughtmechanix.specialroutes.utils.UserContext;
import com.thoughtmechanix.specialroutes.utils.UserContextHolder;

import java.util.concurrent.Callable;

/**
 * The class to propagate the {@code UserContext} (thread context) from 
 * the parent thread to the Hystrix thread.
 * 
 * <p>When a call is made to the method annotated by the the @HystrixCommand 
 * annotation, Hystrix and Spring Cloud will instantiate an instance of this 
 * class, passing 2 variable: 
 * <ul>
 *   <li>The callable class managed by a Hystrix command pool.
 *   <li>The {@code UserContext} object.
 * </ul>
 * 
 * <p>After instantiating an instance of this class, the call() method will be 
 * invoked.
 *
 * @author  Wuyi Chen
 * @date    03/17/2019
 * @version 1.0
 * @since   1.0
 */
public final class DelegatingUserContextCallable<V> implements Callable<V> {
    private final Callable<V> delegate;
    private       UserContext originalUserContext;

    /**
     * Construct a {@code DelegatingUserContextCallable}.
     * 
     * @param  delegate
     *         The original Callable class that will invoke your Hystrix 
     *         protected code.
     *         
     * @param  userContext
     *         The {@code UserContext} coming in from the parent thread.
     */
    public DelegatingUserContextCallable(Callable<V> delegate, UserContext userContext) {
        this.delegate            = delegate;
        this.originalUserContext = userContext;
    }

    /* (non-Javadoc)
     * @see java.util.concurrent.Callable#call()
     * 
     * This function will be invoked before invoking the method annotated by 
     * the @HystrixCommand annotation.
     */
    @Override
    public V call() throws Exception {
        UserContextHolder.setContext(originalUserContext);  // Set the UserContext.

        try {
            return delegate.call();                         // Invoke the method annotated by the @HystrixCommand annotation.
        } finally {
            this.originalUserContext = null;
        }
    }

    /**
     * Create a {@code DelegatingUserContextCallable}.
     * 
     * @param  delegate
     *         The original Callable class that will invoke your Hystrix 
     *         protected code.
     *         
     * @param  userContext
     *         The {@code UserContext} coming in from the parent thread.
     *         
     * @return  The object of {@code DelegatingUserContextCallable}.
     */
    public static <V> Callable<V> create(Callable<V> delegate, UserContext userContext) {
        return new DelegatingUserContextCallable<V>(delegate, userContext);
    }
}