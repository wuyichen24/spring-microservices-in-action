package com.thoughtmechanix.authentication.utils;

import org.springframework.util.Assert;

/**
 * The static class to store the {@code UserContext} in a {@code ThreadLocal} 
 * class.
 * 
 * @author  Wuyi Chen
 * @date    03/14/2019
 * @version 1.0
 * @since   1.0
 */
public class UserContextHolder {
	private UserContextHolder() {}
	
	// The UserContext is stored in a static ThreadLocal variable
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    /**
     * Retrieve the {@code UserContext} object.
     * 
     * @return  The {@code UserContext} object.
     */
    public static final UserContext getContext(){
        UserContext context = userContext.get();

        if (context == null) {
            context = createEmptyContext();
            userContext.set(context);
        }
        
        return userContext.get();
    }

    /**
     * Set the {@code UserContext} object
     * 
     * @param  context
     *         The {@code UserContext} object needs to be set. 
     */
    public static final void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        userContext.set(context);
    }

    /**
     * Create a new empty {@code UserContext} object.
     * 
     * @return The newly created {@code UserContext} object.
     */
    public static final UserContext createEmptyContext(){
        return new UserContext();
    }
}
