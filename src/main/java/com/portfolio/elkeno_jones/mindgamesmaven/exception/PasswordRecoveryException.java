package com.portfolio.elkeno_jones.mindgamesmaven.exception;

/**
 *
 * @author Elkeno
 */
public class PasswordRecoveryException extends Exception {
    private String message;
    
    public PasswordRecoveryException() {
        this.message = "Failed to recover password. The email may not exist";
    }
    
    public PasswordRecoveryException(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}
