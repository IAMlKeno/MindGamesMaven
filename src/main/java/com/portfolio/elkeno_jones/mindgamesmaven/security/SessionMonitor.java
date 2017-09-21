package com.portfolio.elkeno_jones.mindgamesmaven.security;

import com.portfolio.elkeno_jones.mindgamesmaven.service.UserServiceImpl;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Elkeno Jones
 */
public abstract class SessionMonitor {
    
    @Autowired
    UserServiceImpl userService;
    
    public String validateSessionToken(Integer id){
        String token = null;
        try {
            
            
        } catch(Exception e) {
            return null;
        }
        
        return token;
    };
    
    public Date findLastActiveByUserId(Integer userId){
        return null;
    }
    
    public String generateNewToken(String username, String password){
        String token = null;
        
        try {
            username = userService.loadUserByUsername(username, password);
            
            if(username != null) {
                token = UUID.randomUUID().toString().toUpperCase() +
                    "|" + username;
                System.out.println("Token generated [" + token + "]");
            }
        } catch (Exception e) {
            
        }
        
        return token;
    }
}
