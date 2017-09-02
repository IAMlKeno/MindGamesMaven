package com.portfolio.elkeno_jones.mindgamesmaven.service;

/**
 * This service will be used to monitor and update the security 
 * sessions
 * 
 * @author Elkeno Jones
 */
public interface Security {
    
    /**
     * This method will generate a unique key to be used for the session
     * to monitor user access
     * 
     * @param username
     * @return 
     */
    public String generateSessionToken(String username);
    
    public boolean checkAccess(String sessionToken);
}
