package com.portfolio.elkeno_jones.mindgamesmaven.mvc;

import com.google.gson.Gson;
import com.portfolio.elkeno_jones.mindgamesmaven.dao.FeatureDao;
import com.portfolio.elkeno_jones.mindgamesmaven.dao.IdeaDao;
import com.portfolio.elkeno_jones.mindgamesmaven.dao.UserDao;
import com.portfolio.elkeno_jones.mindgamesmaven.model.Feature;
import com.portfolio.elkeno_jones.mindgamesmaven.model.Idea;
import com.portfolio.elkeno_jones.mindgamesmaven.model.IdeaWithFeatures;
import com.portfolio.elkeno_jones.mindgamesmaven.service.SecurityImpl;
import com.portfolio.elkeno_jones.mindgamesmaven.util.FeatureUtil;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author Elkeno
 */
@Controller
@Configuration
@ComponentScan("com.portfolio.elkeno_jones.mindgamesmaven.dao")
@SessionAttributes("ideaWrapper")
public class DevelopController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private IdeaDao ideaDao;
    @Autowired
    private FeatureDao featureDao;
    @Autowired
    private SecurityImpl sec;

    private static final String IDEA_WRAPPER = "ideaWrapper";
    private static final String NEW_IDEA = "newIdea";
    private static final String IDEA_LIST = "ideaList";

    private static final String DEVELOP_IDEA_VIEW = "developIdea";
    private static final String IDEA_HUB_VIEW = "ideaHub";

    private static final String REMOVE_FEATURE_URL = "/develop/feature/remove";
    private static final String REMOVE_IDEA_URL = "/develop/idea/remove";
    
    private static final String SEARCH_RESULT_URL = "/search/result";

    private static final String IDEA_HUB_URL = "/ideaHub";

    private static final String REDIRECT_VIEW = "/redirect";
    private static final String ERROR_VIEW = "error";

    @RequestMapping(value = REMOVE_FEATURE_URL, method = RequestMethod.POST)
    public String removeFeature(Model model,
            @ModelAttribute("ideaWrapper") IdeaWithFeatures ideaWrapper,
            HttpServletRequest req,
            @RequestParam("updateFeatureId") Integer featureId) {

        HttpSession ses = req.getSession();
        String token = (String) ses.getAttribute("userToken");
        Integer userId = (Integer) ses.getAttribute("userId");
        if (!sec.checkAccess(token, userId)) {
            model.addAttribute("errMessage", "ACCESS DENIED");
            model.addAttribute("redirectErrorUrl", "auth");

            return ERROR_VIEW;
        }

        List<Feature> newFeatList = new ArrayList<Feature>();
        for (Feature feat : ideaWrapper.getFeatures()) {
            if (!feat.getFeatureId().equals(featureId)) {
                newFeatList.add(feat);
            }else {
                featureDao.removeFeature(feat);
            }
        }
        ideaWrapper.setFeatures(newFeatList);
        
        Gson gson = new Gson();
        Map<Integer, String> featMap = FeatureUtil.mapIdToFeature(ideaWrapper.getFeatures());

        model.addAttribute("ideaWrapper", ideaWrapper);
        model.addAttribute("featMap", gson.toJson(featMap));

        return DEVELOP_IDEA_VIEW;
    }

    @RequestMapping(value = REMOVE_IDEA_URL, method = RequestMethod.GET)
    public String removeIdea(Model model,
            @ModelAttribute("ideaWrapper") IdeaWithFeatures ideaWrapper,
            HttpServletRequest req) {

        HttpSession ses = req.getSession();
        String token = (String) ses.getAttribute("userToken");
        Integer userId = (Integer) ses.getAttribute("userId");
        if (!sec.checkAccess(token, userId)) {
            model.addAttribute("errMessage", "ACCESS DENIED");
            model.addAttribute("redirectErrorUrl", "auth");

            return ERROR_VIEW;
        }

        String redirectUrl = IDEA_HUB_URL;
        try {
            Idea i = null;
            i = ideaDao.getIdeaById(ideaWrapper.getIdea().getIdeaId());
            if(i != null) {
                for (Feature feat : ideaWrapper.getFeatures()) {
                    featureDao.removeFeatureById(feat.getFeatureId());
                }
                ideaDao.removeIdea(ideaWrapper.getIdea());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("errMessage", e.getMessage());
            redirectUrl = ERROR_VIEW;
        }
        model.addAttribute("redirecUrl", redirectUrl);

        return REDIRECT_VIEW;
    }
    
    @RequestMapping(value = SEARCH_RESULT_URL, method = RequestMethod.GET)
    public String getIdeaSearchResult(Model model,
            HttpServletRequest req,
            @RequestParam("id") Integer id) {

        HttpSession ses = req.getSession();
        String token = (String) ses.getAttribute("userToken");
        Integer userId = (Integer) ses.getAttribute("userId");
        if (!sec.checkAccess(token, userId)) {
            model.addAttribute("errMessage", "ACCESS DENIED");
            model.addAttribute("redirectErrorUrl", "auth");

            return ERROR_VIEW;
        }
        IdeaWithFeatures ideaWrapper = new IdeaWithFeatures();
        ideaWrapper.setFeatures(featureDao.getFeaturesByIdeaId(id));
        ideaWrapper.setIdea(ideaDao.getIdeaById(id));

        model.addAttribute("ideaWrapper", ideaWrapper);

        return DEVELOP_IDEA_VIEW;
    }
}
