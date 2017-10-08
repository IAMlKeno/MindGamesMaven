package com.portfolio.elkeno_jones.mindgamesmaven.dao;

import com.portfolio.elkeno_jones.mindgamesmaven.model.Feature;
import java.util.List;

/**
 *
 * @author Elkeno
 */
public interface FeatureDao {
    
    /**
     * Get the features for an idea
     * 
     * @param ideaId
     * @return 
     */
    public List<Feature> getFeaturesByIdeaId(Integer ideaId);
    
    /**
     * Get a particular feature by its id
     * 
     * @param id
     * @return 
     */
    public Feature getFeatureById(Integer id);
    
    /**
     * Save or update a list of features
     * Returns true/false if successful or not
     * 
     * @param feature
     * @return 
     */
    public boolean saveFeature(Feature feature);
    
    /**
     * Delete feature
     * 
     * @param featureId
     * @return
     */
    public boolean removeFeature(Feature feature);
    
    /**
     * Remove feature by id
     * @param id
     * @return 
     */
    public boolean removeFeatureById(Integer id);
}
