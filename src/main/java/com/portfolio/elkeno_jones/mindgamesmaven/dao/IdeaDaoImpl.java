package com.portfolio.elkeno_jones.mindgamesmaven.dao;

import com.portfolio.elkeno_jones.mindgamesmaven.model.Idea;
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
public class IdeaDaoImpl implements IdeaDao {

    @Autowired
    private SessionFactory sessionFactory;

    public IdeaDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public IdeaDaoImpl(){}

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
    public boolean saveIdea(Idea idea){
        boolean saveSuccessful;
        try{
            sessionFactory.getCurrentSession().saveOrUpdate(idea);
            saveSuccessful = true;
        } catch (HibernateException he){
            System.out.println(he.getMessage());
            saveSuccessful = false;
        }
        return saveSuccessful;
    }
    
    @Override
    @Transactional
    public Idea saveNewIdea(Idea idea){
        boolean saveSuccessful = false;
//        Idea idea = null;
        try{
            if(idea.getIdeaId() <= 0){
                sessionFactory.getCurrentSession().save(idea);
                saveSuccessful = true;
            } else {
                sessionFactory.getCurrentSession().update(idea);
                saveSuccessful = true;
            }
            
        } catch (HibernateException he){
            System.out.println(he.getMessage());
            saveSuccessful = false;
        }
        return idea;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
