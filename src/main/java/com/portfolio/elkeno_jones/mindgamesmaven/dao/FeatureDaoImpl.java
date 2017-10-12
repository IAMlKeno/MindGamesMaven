package com.portfolio.elkeno_jones.mindgamesmaven.dao;

import com.portfolio.elkeno_jones.mindgamesmaven.model.Feature;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Entity;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
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

    public FeatureDaoImpl() {
    }

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
    public boolean saveFeature(Feature feature) {
        boolean saveSuccessful;
        try {
//            for(Feature feat : features){
//                sessionFactory.getCurrentSession().saveOrUpdate(feat);
//            }
            sessionFactory.getCurrentSession().saveOrUpdate(feature);
            saveSuccessful = true;
        } catch (HibernateException he) {
            System.out.println("[Hibernate Exception - save features] " + he.getMessage());
            saveSuccessful = false;
        }

        return saveSuccessful;
    }

    @Override
    @Transactional
    public boolean removeFeature(Feature feature) {
        boolean isSuccess = true;

        try {
            sessionFactory.getCurrentSession().delete(feature);
            sessionFactory.getCurrentSession().flush();
        } catch (HibernateException he) {
            isSuccess = false;
            System.out.println("[Delete feature]: " + he.getMessage());
        }

        return isSuccess;
    }

    @Override
    @Transactional
    public boolean removeFeatureById(Integer id) {
        boolean isSuccess = true;

        try {
            String sql = "delete from Feature where featureid = :id";
            Query q = sessionFactory.getCurrentSession().createQuery(sql);
            q.setParameter("id", id);
            q.executeUpdate();
        } catch (HibernateException he) {
            isSuccess = false;
            System.out.println("[Delete feature]: " + he.getMessage());
        }

        return isSuccess;
    }

    @Override
    @Transactional
    public Integer getMaxFeatureId() {
        Integer id = 0;
        try {
            DetachedCriteria maxId = DetachedCriteria.forClass(Feature.class)
                    .setProjection(Projections.max("featureId"));
            id = (Integer) sessionFactory.getCurrentSession().createCriteria(Feature.class)
                    .setProjection(Projections.max("featureId"))
                    .uniqueResult();
            if (id < 0 || id == null) {
                id = 0;
            }
        } catch (HibernateException he) {
            System.out.println("[Get max feature id]: " + he.getMessage());
            id = 0;
        } catch (Exception e) {
            System.out.println("[Get max feature id]: " + e.getMessage());
            id = 0;
        }

        return id;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
