package com.portfolio.elkeno_jones.mindgamesmaven.service;

import com.portfolio.elkeno_jones.mindgamesmaven.model.Idea;
import com.portfolio.elkeno_jones.mindgamesmaven.model.IdeaWithFeatures;

/**
 *
 * @author Elkeno
 */
public interface IdeaService {

    /**
     * Permanently deletes the idea and its associated features
     * @param ideaId
     * @throws java.lang.Exception 
     */
    public void deleteIdeaAndFeatures(Integer ideaId) throws Exception;
    public void deleteIdeaAndFeature(IdeaWithFeatures ideaWrapper) throws Exception;
    
    /**
     * Saves the idea and its associated features. This method creates a new
     * idea or updates an existing idea
     * @param ideaWrapper 
     * @throws java.lang.Exception 
     * 
     */
    public void saveIdea(IdeaWithFeatures ideaWrapper) throws Exception;
}
