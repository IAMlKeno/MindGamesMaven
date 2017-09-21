package com.portfolio.elkeno_jones.mindgamesmaven.dao;

import com.portfolio.elkeno_jones.mindgamesmaven.model.SecurityObj;

/**
 *
 * @author Elkeno
 */
public interface SecurityDao {
    /**
     * Retrieves the session information for the current user
     * 
     * @param userId user id used to verify which user to get access for
     * @return       Return a SecurityObj holding the user access information
     */
    public SecurityObj getSecurityAccess(Integer userId);
    
    /**
     * Updates or inserts the user access token along with the current date and 
     * time
     * 
     * @param userId  the user id to associate with the current user logged in
     * @param token   The current session token associated with the user
     * @return        Return a SecurityObj holding the user access information
     */
    public SecurityObj updateInsertTokenAccess(Integer userId, String token);
    
    /**
     * Removes the session token and current date and time from the session 
     * table
     * 
     * @param userId The user id associated with the current user id
     * @return       Returns true if the deletion is successful
     */
    public boolean deleteTokenEntry(Integer userId);
}
