package com.portfolio.elkeno_jones.mindgamesmaven.dao;

import com.portfolio.elkeno_jones.mindgamesmaven.model.User;
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
public class UserDaoImpl implements UserDao {

//    @Autowired
    private SessionFactory sessionFactory;

    public UserDaoImpl(){}
    
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public User getUserById(Integer id) {
        return (User) sessionFactory.getCurrentSession()
                .get(User.class, id);
    }
    
    @Override
    @Transactional
    public boolean saveUser(User user){
        boolean saveSuccessful;
        
        try{
            sessionFactory.getCurrentSession().saveOrUpdate(user);
            saveSuccessful = true;
        } catch(HibernateException he){
            System.out.println("[Hibernate Execption] " + he.getMessage());
            saveSuccessful = false;
        }
        
        return saveSuccessful;
    }
    
    @Override
    @Transactional
    public User findUserByUsernameOrEmail(String username){
        User user = null;
        
        try{
            user = (User) sessionFactory.getCurrentSession()
                    .createCriteria(User.class)
                    .add(Restrictions.eq("username", username))
                    .uniqueResult();
            if (user == null) {
                user = (User) sessionFactory.getCurrentSession()
                        .createCriteria(User.class)
                        .add(Restrictions.eq("emailAddress", username))
                        .uniqueResult();
            }
        } catch (HibernateException he){
            System.out.println("[Hibernate Execption] " + he.getMessage());
            return null;
        } catch (Exception ex)
        {
            System.out.println("######EJONES: " + ex.getMessage());
        }
        
        return user;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
