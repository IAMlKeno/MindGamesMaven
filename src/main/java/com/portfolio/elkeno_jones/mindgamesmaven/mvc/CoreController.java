/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.elkeno_jones.mindgamesmaven.mvc;

import com.portfolio.elkeno_jones.mindgamesmaven.dao.UserDao;
import com.portfolio.elkeno_jones.mindgamesmaven.model.User;
import com.portfolio.elkeno_jones.mindgamesmaven.service.SecurityImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Elkeno
 */
@Controller
public class CoreController {

    protected static final String LOGIN_VIEW = "login";
    protected static final String AUTHENTICATE_URL = "/auth";
    protected static final String ACCESS_DENIED_URL = "/Access_Denied";
    protected static final String LOGOUT_URL = "/logout";
    protected static final String LOGIN_URL = "/login";

    private static final String IDEA_HUB_VIEW = "ideaHub";
    private static final String IDEA_HUB_URL = "/ideaHub";

    private static final String REDIRECT_VIEW = "/redirect";

    private static final String REGISTER_URL = "/register";
    private static final String REGISTER_VIEW = "register";

    private static final String ERROR_VIEW = "error";

    @Autowired
    private UserDao userDao;

    @Autowired
    private SecurityImpl sec;

    @RequestMapping(value = AUTHENTICATE_URL)
    public String setLanding(Model model, HttpServletRequest req) {
        String landingPage = "";

        HttpSession session = req.getSession(true);
        session.setAttribute("userToken", "Test");
        landingPage = LOGIN_VIEW;
        model.addAttribute("user", new User());
        return landingPage;
    }

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.POST)
    public String loginPage(HttpServletRequest req,
            Model model,
            @ModelAttribute("user") User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String token;
        String loginView;

        User userVo = userDao.findUserByUsername(username);

        if (userVo.getPassword().equals(password)) {
            token = sec.generateSessionToken(username);
            HttpSession session = req.getSession(true);
            session.setAttribute("userToken", token);
            session.setAttribute("userId", userVo.getUserId());
            sec.beginSession(userVo.getUserId(), token);

            model.addAttribute("redirectUrl", IDEA_HUB_URL);

            loginView = REDIRECT_VIEW;
        } else {
            loginView = LOGIN_VIEW;
        }

        return loginView;
    }

    @RequestMapping(value = LOGOUT_URL, method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        session.removeAttribute("userId");
        session.removeAttribute("userToken");
        sec.removeSession(userId);

        return REDIRECT_VIEW;
    }

    @RequestMapping(value = REGISTER_URL, method = RequestMethod.POST)
    public String addNewUser(Model model,
            @ModelAttribute("user") User userVo,
            HttpServletRequest req) {
        String redirectUrl;

        if (userVo.getUserId() == null) {
            userVo.setUserId(0);
        }
        if (!userDao.saveUser(userVo)) {
            String errMessage = "Error registering new user!";
            model.addAttribute("errMessage", errMessage);
            redirectUrl = ERROR_VIEW;
        } else {
            redirectUrl = IDEA_HUB_VIEW;
            String username = userVo.getUsername();
            String token = sec.generateSessionToken(username);
            HttpSession session = req.getSession(true);

            User dbUser = userDao.findUserByUsername(username);
            userVo.setUserId(dbUser.getUserId());
            
            session.setAttribute("userToken", token);
            session.setAttribute("userId", userVo.getUserId());
            sec.beginSession(userVo.getUserId(), token);
            model.addAttribute("user", userVo);
        }
        model.addAttribute("redirectUrl", redirectUrl);

        return REDIRECT_VIEW;
    }

    @RequestMapping(value = ACCESS_DENIED_URL, method = RequestMethod.GET)
    public String accessDeniedPage(Model model) {
//        model.addAttribute("user", getPrincipal());
        return "accessDenied";
    }

}
