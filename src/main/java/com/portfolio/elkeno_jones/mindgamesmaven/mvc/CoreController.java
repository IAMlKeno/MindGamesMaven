package com.portfolio.elkeno_jones.mindgamesmaven.mvc;

import com.portfolio.elkeno_jones.mindgamesmaven.dao.UserDao;
import com.portfolio.elkeno_jones.mindgamesmaven.model.User;
import com.portfolio.elkeno_jones.mindgamesmaven.service.SecurityImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Configuration
public class CoreController {

    protected static final String LOGIN_VIEW = "login";
    protected static final String AUTHENTICATE_URL = "/auth";
    protected static final String LOGOUT_URL = "/logout";
    protected static final String LOGIN_URL = "/login";

    private static final String IDEA_HUB_VIEW = "ideaHub";
    private static final String IDEA_HUB_URL = "/ideaHub";
    
    private static final String LANDING_VIEW = "landing";
    private static final String LANDING_URL = "/home";

    private static final String ACCOUNT_VIEW = "account";
    private static final String ACCOUNT_URL = "/account";
    private static final String ACCOUNT_UPDATE_URL = "/account/update";

    private static final String REDIRECT_VIEW = "redirect";

    private static final String REGISTER_URL = "/register";
    private static final String REGISTER_VIEW = "register";

    private static final String ERROR_VIEW = "error";

    @Autowired
    private UserDao userDao;

    @Autowired
    private SecurityImpl sec;

    @RequestMapping(value = AUTHENTICATE_URL)
    public String authenticate(Model model, HttpServletRequest req) {
        String landingPage;

        HttpSession session = req.getSession(true);
        if (session.getAttribute("userToken") != null) {
            Integer userId = (Integer) session.getAttribute("userId");
            String token = (String) session.getAttribute("userToken");
            if (sec.checkAccess(token, userId)) {
                model.addAttribute("redirectUrl", IDEA_HUB_URL);
                landingPage = REDIRECT_VIEW;
            } else {
                model.addAttribute("user", new User());
                landingPage = LOGIN_VIEW;
            }
        } else {
            model.addAttribute("user", new User());
            landingPage = LOGIN_VIEW;
        }
        return landingPage;
    }
    
    @RequestMapping(value = LANDING_URL)
    public String setLanding(Model model, HttpServletRequest req) {
        String landingPage;

        HttpSession session = req.getSession(true);
        if (session.getAttribute("userToken") != null) {
            Integer userId = (Integer) session.getAttribute("userId");
            String token = (String) session.getAttribute("userToken");
            if (sec.checkAccess(token, userId)) {
                model.addAttribute("redirectUrl", IDEA_HUB_URL);
                landingPage = REDIRECT_VIEW;
            } else {
                model.addAttribute("user", new User());
                landingPage = LOGIN_VIEW;
            }
        } else {
            model.addAttribute("isLanding", true);
            model.addAttribute("user", new User());
            landingPage = LANDING_VIEW;
        }
        return landingPage;
    }

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.POST)
    public String loginPage(HttpServletRequest req, Model model,
            @ModelAttribute("user") User user, BindingResult result) {

        if (result.hasErrors()) {
            return REDIRECT_VIEW;
        }
        String username = user.getUsername();
        String password = user.getPassword();
        String token;
        String loginView;

        User userVo = userDao.findUserByUsernameOrEmail(username);

        if (userVo != null && userVo.getPassword().equals(password)) {
            token = sec.generateSessionToken(username);
            HttpSession session = req.getSession(true);
            session.setAttribute("userToken", token);
            session.setAttribute("userId", userVo.getUserId());
            sec.beginSession(userVo.getUserId(), token);

            model.addAttribute("redirectUrl", IDEA_HUB_URL);

            loginView = REDIRECT_VIEW;
        } else {
            boolean loginAuth = false;
            model.addAttribute("loginAuth", loginAuth);
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
            model.addAttribute("redirectErrorUrl", AUTHENTICATE_URL);
            redirectUrl = ERROR_VIEW;
        } else {
            redirectUrl = IDEA_HUB_VIEW;
            String username = userVo.getUsername();
            String token = sec.generateSessionToken(username);
            HttpSession session = req.getSession(true);

            User dbUser = userDao.findUserByUsernameOrEmail(username);
            userVo.setUserId(dbUser.getUserId());

            session.setAttribute("userToken", token);
            session.setAttribute("userId", userVo.getUserId());
            sec.beginSession(userVo.getUserId(), token);
            model.addAttribute("user", userVo);
        }
        model.addAttribute("redirectUrl", redirectUrl);

        return REDIRECT_VIEW;
    }

    @RequestMapping(value = "/")
    public String indexer() {
        return REDIRECT_VIEW;
    }

    @RequestMapping(value = ACCOUNT_URL, method = RequestMethod.GET)
    public String viewUserAccount(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String token = (String) session.getAttribute("userToken");

        if (!sec.checkAccess(token, userId)) {
            model.addAttribute("errMessage", "ACCESS DENIED...");
            model.addAttribute("redirectErrorUrl", AUTHENTICATE_URL);

            return ERROR_VIEW;
        }

        User user = userDao.getUserById(userId);
        model.addAttribute("userAccount", user);

        return ACCOUNT_VIEW;
    }

    @RequestMapping(value = ACCOUNT_URL, method = RequestMethod.POST)
    public String updateUserAccount(Model model, HttpServletRequest req,
            @ModelAttribute("userAccount") User user) {
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String token = (String) session.getAttribute("userToken");

        if (!sec.checkAccess(token, userId)) {
            model.addAttribute("errMessage", "ACCESS DENIED...");
            model.addAttribute("redirectErrorUrl", AUTHENTICATE_URL);

            return ERROR_VIEW;
        }

        if (!user.getUserId().equals(userId)) {
            model.addAttribute("errMessage", "ACCESS DENIED...");
            model.addAttribute("redirectErrorUrl", AUTHENTICATE_URL);
            return ERROR_VIEW;
        }
        if (!userDao.saveUser(user)) {
            String errMessage = "Error updating account";
            model.addAttribute("errMessage", errMessage);
            model.addAttribute("redirectErrorUrl", AUTHENTICATE_URL);

            return ERROR_VIEW;
        }
        model.addAttribute("isAccountUpdated", true);
        model.addAttribute("userAccount", user);

        return ACCOUNT_VIEW;
    }
}
