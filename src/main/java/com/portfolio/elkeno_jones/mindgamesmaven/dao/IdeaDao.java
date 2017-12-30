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
     * @param orderBy
     * @param orderDir
     * @return 
     */
    public List<Idea> getIdeasByUserId(Integer userId, String orderBy, String orderDir);
    
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
    
    /**
     * Delete feature
     * 
     * @param idea
     * @return
     */
    public boolean removeIdea(Idea idea);
    
    /**
     * Search for matching user ideas 
     * @param srchStr
     * @return 
     */
    public List<Idea> searchIdeaTitle(String srchStr, Integer userId);
}
