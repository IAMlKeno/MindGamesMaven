package com.portfolio.elkeno_jones.mindgamesmaven.dao;

import com.portfolio.elkeno_jones.mindgamesmaven.model.User;
import java.util.List;

/**
 *
 * @author Elkeno
 */
public interface UserDao {
    
    /**
     * Gets the user by id
     * 
     * @param id
     * @return 
     */
    public User getUserById(Integer id);
    
    /**
     * Saves or updates user
     * 
     * @param user
     * @return 
     */
    public boolean saveUser(User user);
    
    /**
     * Find the user by the supplied username
     * 
     * @param username
     * @return 
     */
    public User findUserByUsernameOrEmail(String username);
}
