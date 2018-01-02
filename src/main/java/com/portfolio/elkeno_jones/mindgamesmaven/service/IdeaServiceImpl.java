package com.portfolio.elkeno_jones.mindgamesmaven.service;

import com.portfolio.elkeno_jones.mindgamesmaven.dao.FeatureDao;
import com.portfolio.elkeno_jones.mindgamesmaven.dao.IdeaDao;
import com.portfolio.elkeno_jones.mindgamesmaven.model.Feature;
import com.portfolio.elkeno_jones.mindgamesmaven.model.Idea;
import com.portfolio.elkeno_jones.mindgamesmaven.model.IdeaWithFeatures;
import java.util.List;

/**
 *
 * @author Elkeno
 */
public class IdeaServiceImpl implements IdeaService {

    protected IdeaDao ideaDao;
    protected FeatureDao featureDao;

    @Override
    public void deleteIdeaAndFeatures(Integer ideaId) throws Exception {
        Idea idea;
        List<Feature> features;
        try {
            idea = ideaDao.getIdeaById(ideaId);
            features = featureDao.getFeaturesByIdeaId(ideaId);
            if (idea != null) {
                if (!features.isEmpty()) {
                    for (Feature feat : features) {
                        featureDao.removeFeatureById(feat.getFeatureId());
                    }
                }
                ideaDao.removeIdea(idea);
            }
        } catch (Exception ex) {
            //TODO create specific exception
            throw ex;
        }
    }

    @Override
    public void deleteIdeaAndFeature(IdeaWithFeatures ideaWrapper) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveIdea(IdeaWithFeatures ideaWrapper) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public IdeaDao getIdeaDao() {
        return ideaDao;
    }

    public void setIdeaDao(IdeaDao ideDao) {
        this.ideaDao = ideDao;
    }

    public FeatureDao getFeatureDao() {
        return featureDao;
    }

    public void setFeatureDao(FeatureDao featureDao) {
        this.featureDao = featureDao;
    }
}
