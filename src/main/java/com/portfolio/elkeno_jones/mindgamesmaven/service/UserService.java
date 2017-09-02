package com.portfolio.elkeno_jones.mindgamesmaven.service;

import com.portfolio.elkeno_jones.mindgamesmaven.model.User;

/**
 * This Interface is to be used for general and specific user
 * related tasks
 * 
 * @author Elkeno Jones
 */
public interface UserService {
    
    /**
     * This method uses the passed parameters to authenticate the user
     * 
     * @param username
     * @param password
     * @return 
     */
    public User authenticateUser(String username, String password);
    
    /**
     * This method will be used for resetting passwords based on the 
     * email
     * 
     * @param email
     * @return 
     */
    public String resetPassword(String email);
}
