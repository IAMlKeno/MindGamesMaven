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
    
    /**
     * Checks the current session validity. 
     * 
     * @param sessionToken   Current session token
     * @param userId         Current user id
     * @return               Returns true if session is valid
     */
    public boolean checkAccess(String sessionToken, Integer userId);
    
    /**
     * Begins the session logged in the database
     * 
     * @param userId         Current user id returned
     * @param sessionToken   Current session token to be stored
     * @return               Return true if session is successfully stored and 
     * false on failure
     */
    public boolean beginSession(Integer userId, String sessionToken);
    
    /**
     * Removes the session
     * 
     * @param userId  user id used to identify which entry to remove
     * @return        Return true if session is successfully stored and 
     * false on failure
     */
    public boolean removeSession(Integer userId);
}
