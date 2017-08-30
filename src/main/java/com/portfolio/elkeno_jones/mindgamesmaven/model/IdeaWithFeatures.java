package com.portfolio.elkeno_jones.mindgamesmaven.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Elkeno
 */
public class IdeaWithFeatures implements Serializable {
    private Idea idea;
    private List<Feature> features;
    private Feature newFeature;

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public Feature getNewFeature() {
        return newFeature;
    }

    public void setNewFeature(Feature newFeature) {
        this.newFeature = newFeature;
    }

    
}
