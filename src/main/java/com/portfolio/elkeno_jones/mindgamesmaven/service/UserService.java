package com.portfolio.elkeno_jones.mindgamesmaven.service;

import com.portfolio.elkeno_jones.mindgamesmaven.exception.PasswordRecoveryException;
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
     * Method confirms that the user email exists, if so, sends the recovery 
     * password to the email passed in
     * @param email email address to send recovery password to
     */
    public void sendUserPassword(String email) throws PasswordRecoveryException;
}
