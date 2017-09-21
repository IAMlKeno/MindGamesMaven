package com.portfolio.elkeno_jones.mindgamesmaven.service;

import com.portfolio.elkeno_jones.mindgamesmaven.dao.UserDaoImpl;
import com.portfolio.elkeno_jones.mindgamesmaven.model.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Elkeno Jones
 */
public class UserServiceImpl {

    @Autowired
    private UserDaoImpl userDao;

    @Transactional(readOnly = true)
    public String loadUserByUsername(String username, String password) {
        try{
            User user = userDao.findUserByUsername(username);

            if (user == null) {
                System.out.println("User not found");
                throw new HibernateException ("Username not found");
            } else {
                if(password.equals(user.getPassword())){
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

//    private List<GrantedAuthority> getGrantedAuthorities(User user) {
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//
////        for (UserProfile userProfile : user.getUserProfiles()) {
////            System.out.println("UserProfile : " + userProfile);
////            authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getType()));
////        }
////        System.out.print("authorities :" + authorities);
//        return authorities;
//    }

}
