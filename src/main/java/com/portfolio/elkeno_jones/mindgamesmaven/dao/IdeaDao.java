/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.elkeno_jones.mindgamesmaven.dao;

import com.portfolio.elkeno_jones.mindgamesmaven.model.Idea;
import java.util.List;

/**
 *
 * @author Elkeno
 */
public interface IdeaDao {
    
    /**
     * Get list of ideas by the user (user id)
     * 
     * @param userId
     * @return 
     */
    public List<Idea> getIdeasByUserId(Integer userId);
    
    /**
     * Get idea by the id
     * 
     * @param id
     * @return 
     */
    public Idea getIdeaById(Integer id);
    
    public Idea saveNewIdea(Idea idea);
    
    public Idea getIdeaByTitle(String title);
    
    /**
     * Save the idea and return true/false if successful
     * 
     * @param idea
     * @return 
     */
    public boolean saveIdea(Idea idea);
}
