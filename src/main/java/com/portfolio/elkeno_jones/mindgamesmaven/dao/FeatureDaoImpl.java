package com.portfolio.elkeno_jones.mindgamesmaven.dao;

import com.portfolio.elkeno_jones.mindgamesmaven.model.Feature;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Entity;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Elkeno
 */
@Entity
public class FeatureDaoImpl implements FeatureDao {
    @Autowired
    private SessionFactory sessionFactory;
 
    public FeatureDaoImpl(){}
    
    public FeatureDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    @Transactional
    public List<Feature> getFeaturesByIdeaId(Integer ideaId) {
        List<Feature> features = (List<Feature>) sessionFactory.getCurrentSession()
                .createCriteria(Feature.class)
                .add(Restrictions.eq("ideaId", ideaId))
                .list();
        
        return features;
    }

    @Override
    @Transactional
    public Feature getFeatureById(Integer id) {
        Feature feature = (Feature) sessionFactory.getCurrentSession()
                                .get(Feature.class, id);
        
        return feature;
    }
    
    @Override
    @Transactional
    public boolean saveFeature(Feature feature){
        boolean saveSuccessful;
        try{
//            for(Feature feat : features){
//                sessionFactory.getCurrentSession().saveOrUpdate(feat);
//            }
            sessionFactory.getCurrentSession().saveOrUpdate(feature);
            saveSuccessful = true;
        } catch (HibernateException he){
            System.out.println("[Hibernate Exception - save features] " + he.getMessage());
            saveSuccessful = false;
        }
        
        return saveSuccessful;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
