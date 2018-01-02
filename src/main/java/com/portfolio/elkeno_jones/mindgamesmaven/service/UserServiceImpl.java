package com.portfolio.elkeno_jones.mindgamesmaven.service;

import com.portfolio.elkeno_jones.mindgamesmaven.dao.UserDao;
import com.portfolio.elkeno_jones.mindgamesmaven.exception.PasswordRecoveryException;
import com.portfolio.elkeno_jones.mindgamesmaven.model.User;
import com.portfolio.elkeno_jones.mindgamesmaven.util.JavaMailer;
import org.hibernate.HibernateException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Elkeno Jones
 */
public class UserServiceImpl implements UserService {

    protected UserDao userDao;

    @Transactional(readOnly = true)
    public String loadUserByUsername(String username, String password) {
        try {
            User user = userDao.findUserByUsernameOrEmail(username);

            if (user == null) {
                System.out.println("User not found");
                throw new HibernateException("Username not found");
            } else {
                if (password.equals(user.getPassword())) {
                    return user.getUsername();
                } else {
                    throw new HibernateException("Invalid password");
                }
            }
        } catch (HibernateException he) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void sendUserPassword(String email) throws PasswordRecoveryException {
        try {
            User userVo = userDao.findUserByUsernameOrEmail(email);
            if (userVo == null) {
                throw new PasswordRecoveryException();
            } else {
                sendRecoveryEmail(userVo.getEmailAddress(), userVo.getPassword());
            }
        } catch (Exception ex) {
            System.out.println("######EJONES: " + ex.getStackTrace());
        }
    }

    private void sendRecoveryEmail(String emailTo, String password) {
        String fromEmail = "norepley.ideaorganizer@gmail.com";
        String subject = "Idea Organizer Password Recovery";
        String emailBody = "Your recovery password is: " + password;
        ApplicationContext context
                = new ClassPathXmlApplicationContext("Spring-Mail.xml");

        JavaMailer mm = (JavaMailer) context.getBean("javaMailer");
        mm.sendMail(fromEmail, emailTo, subject, emailBody);
    }

    @Override
    public User authenticateUser(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public UserDao getUserDao() {
        return this.userDao;
    }
}
