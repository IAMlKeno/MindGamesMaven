package com.portfolio.elkeno_jones.mindgamesmaven.dao;

import com.portfolio.elkeno_jones.mindgamesmaven.model.Idea;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Entity;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Elkeno
 */
@Entity
public class IdeaDaoImpl implements IdeaDao {

    @Autowired
    private SessionFactory sessionFactory;

    public IdeaDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public IdeaDaoImpl() {
    }

    @Override
    @Transactional
    public List<Idea> getIdeasByUserId(Integer userId) {
        List<Idea> idea = (List<Idea>) sessionFactory.getCurrentSession()
                .createCriteria(Idea.class)
                .add(Restrictions.eq("userId", userId))
                .list();

        return idea;
    }

    @Override
    @Transactional
    public Idea getIdeaById(Integer id) {
        return (Idea) sessionFactory.getCurrentSession()
                .get(Idea.class, id);
    }

    @Override
    @Transactional
    public Idea getIdeaByTitle(String title) {
        return (Idea) sessionFactory.getCurrentSession()
                .createCriteria(Idea.class)
                .add(Restrictions.eq("ideaTitle", title));
    }

    @Override
    @Transactional
    public boolean saveIdea(Idea idea) {
        boolean saveSuccessful;
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(idea);
            saveSuccessful = true;
        } catch (HibernateException he) {
            System.out.println(he.getMessage());
            saveSuccessful = false;
        }
        return saveSuccessful;
    }

    @Override
    @Transactional
    public Idea saveNewIdea(Idea idea) {
        boolean saveSuccessful = false;
//        Idea idea = null;
        try {
            if(idea.getAlias() != null && idea.getAlias().equalsIgnoreCase("")) {
                idea.setAlias(null);
            }
            if (idea.getIdeaId() <= 0) {
                sessionFactory.getCurrentSession().save(idea);
                saveSuccessful = true;
            } else {
                sessionFactory.getCurrentSession().update(idea);
                saveSuccessful = true;
            }

        } catch (HibernateException he) {
            System.out.println(he.getMessage());
            saveSuccessful = false;
        }
        return idea;
    }

    @Override
    @Transactional
    public boolean removeIdea(Idea idea) {
        boolean isSuccess = true;

        try {
            String sql = "delete from Idea where ideaId = :id";
            Query q = sessionFactory.getCurrentSession().createQuery(sql);
            q.setParameter("id", idea.getIdeaId());

            q.executeUpdate();
        } catch (HibernateException he) {
            System.out.println("[Delete idea]: " + he.getMessage());
            isSuccess = false;
        } catch (Exception e) {
            System.out.println("[Delete idea]: " + e.getMessage());
            isSuccess = false;
        }

        return isSuccess;
    }

    @Override
    @Transactional
    public List<Idea> searchIdeaTitle(String srchStr, Integer userId) {
        List<Idea> results = null;

        try {
            results = (List<Idea>) sessionFactory.getCurrentSession()
                    .createCriteria(Idea.class)
                    .add(Restrictions.like("ideaTitle", "%" + srchStr + "%"))
                    .add(Restrictions.eq("userId", userId))
                    .addOrder(Property.forName("ideaTitle").asc())
                    .list();
        } catch (HibernateException he) {
            System.out.println("[Delete idea]: " + he.getMessage());
        } catch (Exception e) {
            System.out.println("[Delete idea]: " + e.getMessage());
        }

        return results;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
