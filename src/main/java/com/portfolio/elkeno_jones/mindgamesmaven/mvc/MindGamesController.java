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
import java.util.Date;
import java.util.HashMap;
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
public class MindGamesController {

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

    /* Idea hub view */
    private static final String IDEA_HUB_URL = "/ideaHub";
    private static final String IDEA_HUB_VIEW = "ideaHub";
    private static final String IDEA_HUB_UPDATE_PROGRESS_URL = "/ideaHub/progress";
    private static final String IDEA_HUB_UPDATE_STATUS_URL = "/ideaHub/status";

    /* Save*/
    private static final String SAVE_URL = "/save";

    /* Develop Idea */
    private static final String DEVELOP_IDEA_VIEW = "developIdea";
    private static final String DEVELOP_NEW_IDEA_URL = "/develop/newIdea";
    private static final String DEVELOP_IDEA_URL = "/develop";
    private static final String ADD_FEATURE_URL = "/develop/add";
    private static final String UPDATE_FEATURE_URL = "/develop/update";

    /* Redirect and error*/
    private static final String ERROR_VIEW = "error";
    private static final String REDIRECT_VIEW = "/redirect";

    /* */
    private static final String GET_FEATURE_URL = "/develop/feature";
    private static final String UPDATE_IDEA_TITLE_URL = "/develop/idea/update";

    private static final String SEARCH_URL = "/search";

    @RequestMapping(value = IDEA_HUB_URL)
    public String home(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Idea newIdea = new Idea();
        Integer userId = (Integer) session.getAttribute("userId");

        String token = (String) session.getAttribute("userToken");

        if (!sec.checkAccess(token, userId)) {
            model.addAttribute("errMessage", "ACCESS DENIED...");
            model.addAttribute("redirectErrorUrl", "auth");

            return ERROR_VIEW;
        }

        List<Idea> userIdeas = ideaDao.getIdeasByUserId(userId);
        List<IdeaWithFeatures> ideaList = new ArrayList<IdeaWithFeatures>();

        for (Idea tempIdea : userIdeas) {
            IdeaWithFeatures ideaWrapper = new IdeaWithFeatures();
            List<Feature> features = featureDao.getFeaturesByIdeaId(tempIdea.getIdeaId());
            ideaWrapper.setIdea(tempIdea);
            ideaWrapper.setFeatures(features);

            ideaList.add(ideaWrapper);
        }

        model.addAttribute("token", session.getAttribute("userToken"));
        model.addAttribute("ideaList", ideaList);
        model.addAttribute("newIdea", newIdea);
        model.addAttribute(IDEA_WRAPPER, new IdeaWithFeatures());

        return IDEA_HUB_VIEW;
    }

    @RequestMapping(value = DEVELOP_IDEA_URL, method = {RequestMethod.POST, RequestMethod.GET})
    public String developIdea(Model model,
            @RequestParam(value = "ideaId", required = false) String ideaId,
            @RequestParam(value = "reload", required = false) Boolean reload,
            @ModelAttribute(value = "ideaWrapper") IdeaWithFeatures theIdeaWrapper,
            HttpServletRequest req) {
        Integer id;
        HttpSession ses = req.getSession();
        String token = (String) ses.getAttribute("userToken");
        Integer userId = (Integer) ses.getAttribute("userId");

        if (!sec.checkAccess(token, userId)) {
            model.addAttribute("errMessage", "ACCESS DENIED");
            model.addAttribute("redirectErrorUrl", "auth");

            return ERROR_VIEW;
        }
        try {
            IdeaWithFeatures ideaWrapper;
            if (reload != null && reload == true) {
                ideaWrapper = theIdeaWrapper;
            } else {
                id = Integer.parseInt(ideaId);
                ideaWrapper = new IdeaWithFeatures();
                Idea newIdea = ideaDao.getIdeaById(id);
                List<Feature> features = featureDao.getFeaturesByIdeaId(id);

                ideaWrapper.setFeatures(features);
                ideaWrapper.setIdea(newIdea);
                ideaWrapper.setNewFeature(new Feature());
            }
            Gson gson = new Gson();
            Map<Integer, String> featMap = FeatureUtil.mapIdToFeature(ideaWrapper.getFeatures());
            
            model.addAttribute("featMap", gson.toJson(featMap));
            model.addAttribute("ideaWrapper", ideaWrapper);
        } catch (NumberFormatException nfe) {
            String errMessage = "[ Invalid id type - developIdea ] " + nfe.getMessage();
            model.addAttribute("errMessage", errMessage);
        } catch (HibernateException he) {
            String errMessage = "[ Hibernate error - developIdea ] " + he.getMessage();
            model.addAttribute("errMessage", errMessage);//do something
        } catch (Exception e) {
            String errMessage = "[ Java Exception - developIdea ] " + e.getMessage();
            model.addAttribute("errMessage", errMessage);
        }

        return DEVELOP_IDEA_VIEW;
    }

    @RequestMapping(value = ADD_FEATURE_URL, method = RequestMethod.POST)
    public String addFeature(Model model,
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

        Gson gson = new Gson();
        Feature newFeat = ideaWrapper.getNewFeature();
        Map<Integer, String> featMap = FeatureUtil.mapIdToFeature(ideaWrapper.getFeatures());

        Integer maxId = 0;
        Integer returnedId = featureDao.getMaxFeatureId();
        if(returnedId >= 0) {
            maxId = returnedId + 1;
        }
        while (featMap.containsKey(maxId)) {
            maxId++;
        }
        newFeat.setFeatureId(maxId);
        featMap.put(newFeat.getFeatureId(), newFeat.getDescriptionLong());
        ideaWrapper.getFeatures().add(newFeat);
        ideaWrapper.setNewFeature(new Feature());

        model.addAttribute("ideaWrapper", ideaWrapper);
        model.addAttribute("featMap", gson.toJson(featMap));

        model.addAttribute("redirectUrl", "/develop?reload=true");
//        model.addAttribute("reload", true);
        return REDIRECT_VIEW;
    }

    @RequestMapping(value = UPDATE_FEATURE_URL, method = RequestMethod.POST)
    public String updateFeature(Model model,
            @ModelAttribute("ideaWrapper") IdeaWithFeatures ideaWrapper,
            HttpServletRequest req,
            @RequestParam("descriptionShort") String descriptionShort,
            @RequestParam("descriptionLong") String descriptionLong,
            @RequestParam("updateFeatureId") Integer featId) {

        HttpSession ses = req.getSession();
        String token = (String) ses.getAttribute("userToken");
        Integer userId = (Integer) ses.getAttribute("userId");
        if (!sec.checkAccess(token, userId)) {
            model.addAttribute("errMessage", "ACCESS DENIED");
            model.addAttribute("redirectErrorUrl", "auth");

            return ERROR_VIEW;
        }

        Map<Integer, String> featMap = FeatureUtil.mapIdToFeature(ideaWrapper.getFeatures());

        for (Feature feat : ideaWrapper.getFeatures()) {
            if (feat.getFeatureId() != null && feat.getFeatureId().equals(featId)) {
                feat.setDescriptionLong(descriptionLong);
                feat.setDescriptionShort(descriptionShort);
                featMap.put(feat.getFeatureId(), feat.getDescriptionLong());
                break;
            }
        }

        model.addAttribute("ideaWrapper", ideaWrapper);

        Gson gson = new Gson();
        model.addAttribute("featMap", gson.toJson(featMap));

        return DEVELOP_IDEA_VIEW;
    }

    @RequestMapping(value = SAVE_URL, method = RequestMethod.GET)
    public String saveIdea(Model model,
            @ModelAttribute("ideaWrapper") IdeaWithFeatures ideaWrapper,
            HttpServletRequest req) {
        boolean saveSuccessful;
        boolean ideaSaveSuccessful = false;
        String redirectUrl = "";
        HttpSession ses = req.getSession();
        String token = (String) ses.getAttribute("userToken");
        Integer userId = (Integer) ses.getAttribute("userId");

        if (!sec.checkAccess(token, userId)) {
            model.addAttribute("errMessage", "ACCESS DENIED");
            model.addAttribute("redirectErrorUrl", "auth");

            return ERROR_VIEW;
        }

        try {
            try {
                if (ideaWrapper.getIdea().getUserId() == null) {
                    ideaWrapper.getIdea().setUserId(userId);
                }

                ideaWrapper.setIdea(
                        ideaDao.saveNewIdea(ideaWrapper.getIdea())
                );
                ideaSaveSuccessful = true;
            } catch (HibernateException he) {
                /* TODO: handle exception */
            }

            List<Feature> correctedFeatures
                    = validateFeatureToIdea(ideaWrapper.getFeatures(), ideaWrapper.getIdea());
            saveSuccessful = true;
            for (Feature feature : correctedFeatures) {
                if (!featureDao.saveFeature(feature)) {
                    saveSuccessful = false;
                }
            }
            if (saveSuccessful && ideaSaveSuccessful) {
                String location = "/MindGamesMaven";
                model.addAttribute("redirectUrl", IDEA_HUB_URL);
                redirectUrl = REDIRECT_VIEW;
            } else {
                redirectUrl = ERROR_VIEW;
                String errMessage = "Failed to save idea and features";
                model.addAttribute("redirectUrl", "auth");
                model.addAttribute("errMessage", errMessage);
            }
        } catch (Exception e) {
            /* TODO: handle exception */
        }
        return redirectUrl;
    }

    @RequestMapping(value = DEVELOP_NEW_IDEA_URL, method = RequestMethod.POST)
    public String developNewIdea(Model model,
            @ModelAttribute("newIdea") Idea idea,
            HttpServletRequest req) {
        HttpSession ses = req.getSession();
        String token = (String) ses.getAttribute("userToken");
        Integer userId = (Integer) ses.getAttribute("userId");
        if (!sec.checkAccess(token, userId)) {
            model.addAttribute("errMessage", "ACCESS DENIED");
            model.addAttribute("redirectErrorUrl", "auth");

            return ERROR_VIEW;
        }
        try {
            idea.setIdeaId(0);
            Date curDate = new Date();
            idea.setDateCreated(curDate.getTime());

            IdeaWithFeatures ideaWrapper = new IdeaWithFeatures();
            List<Feature> features = new ArrayList<Feature>();

            ideaWrapper.setFeatures(features);
            ideaWrapper.setIdea(idea);
            ideaWrapper.setNewFeature(new Feature());

            model.addAttribute("ideaWrapper", ideaWrapper);
        } catch (HibernateException he) {
            String errMessage = "[ Hibernate error - developNewIdea] " + he.getMessage();
            model.addAttribute("errMessage", errMessage);//do something
        } catch (Exception e) {
            String errMessage = "[ Java Exception - developNewIdea] " + e.getMessage();
            model.addAttribute("errMessage", errMessage);
        }

        return DEVELOP_IDEA_VIEW;
    }

    @RequestMapping(value = REDIRECT_VIEW)
    public String redirect(Model model,
            @ModelAttribute("redirectUrl") String redirect) {

        model.addAttribute("redirectUrl", redirect);

        return REDIRECT_VIEW;
    }

    @RequestMapping(value = IDEA_HUB_UPDATE_PROGRESS_URL, method = RequestMethod.GET)
    public ResponseEntity<?> updateIdeaProgrss(@RequestParam("ideaId") int id,
            @RequestParam("action") boolean action,
            @RequestParam("otherAction") boolean otherAction) {
        Idea theIdea = ideaDao.getIdeaById(id);
        theIdea.setIdeaId(id);
        theIdea.setIsInProgress(action);
        theIdea.setIsCompleted(otherAction);

        if (ideaDao.saveIdea(theIdea)) {
            return new ResponseEntity("Good", HttpStatus.OK);
        } else {
            return new ResponseEntity("Bad", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = IDEA_HUB_UPDATE_STATUS_URL, method = RequestMethod.GET)
    public ResponseEntity<?> updateIdeaStatus(@RequestParam("ideaId") int id,
            @RequestParam("action") boolean action,
            @RequestParam("otherAction") boolean otherAction) {
        Idea theIdea = ideaDao.getIdeaById(id);
        theIdea.setIdeaId(id);
        theIdea.setIsCompleted(action);
        theIdea.setIsInProgress(otherAction);

        if (ideaDao.saveIdea(theIdea)) {
            return new ResponseEntity("Good", HttpStatus.OK);
        } else {
            return new ResponseEntity("Bad", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = GET_FEATURE_URL, method = RequestMethod.GET)
    public ResponseEntity<?> getFeature(@RequestParam("featureId") Integer featureId,
            HttpServletRequest req) {

        HttpSession ses = req.getSession();
        String token = (String) ses.getAttribute("userToken");
        Integer userId = (Integer) ses.getAttribute("userId");
        if (!sec.checkAccess(token, userId)) {
//            model.addAttribute("errMessage", "ACCESS DENIED");
//            model.addAttribute("redirectErrorUrl", "auth");

            return new ResponseEntity("ACCESS DENIED", HttpStatus.FORBIDDEN);
        }

        String featureJson = "";
        Gson gson = new Gson();
        Feature theFeat = featureDao.getFeatureById(featureId);
        featureJson = gson.toJson(theFeat);

        return new ResponseEntity(featureJson, HttpStatus.OK);
    }

    @RequestMapping(value = UPDATE_IDEA_TITLE_URL, method = RequestMethod.POST)
    public String setIdeaTitle(Model model,
            @ModelAttribute("ideaWrapper") IdeaWithFeatures ideaWrapper,
            HttpServletRequest req,
            @RequestParam("theIdeaTitle") String theIdeaTitle,
            @RequestParam("ideaRating") String ideaRating) {

        HttpSession ses = req.getSession();
        String token = (String) ses.getAttribute("userToken");
        Integer userId = (Integer) ses.getAttribute("userId");
        if (!sec.checkAccess(token, userId)) {
            model.addAttribute("errMessage", "ACCESS DENIED");
            model.addAttribute("redirectErrorUrl", "auth");

            return ERROR_VIEW;
        }
        Gson gson = new Gson();
        Map<Integer, String> featMap = FeatureUtil.mapIdToFeature(ideaWrapper.getFeatures());
        ideaWrapper.getIdea().setIdeaTitle(theIdeaTitle);
        ideaWrapper.getIdea().setRating(ideaRating);
        model.addAttribute("ideaWrapper", ideaWrapper);
        model.addAttribute("featMap", gson.toJson(featMap));

        return DEVELOP_IDEA_VIEW;
    }

    @RequestMapping(value = "/develop/reload")
    public String test(Model model,
            @ModelAttribute("ideaWrapper") IdeaWithFeatures ideaWrapper) {

        model.addAttribute("featMap", FeatureUtil.mapIdToFeature(ideaWrapper.getFeatures()));

        return DEVELOP_IDEA_VIEW;
    }

    @RequestMapping(value = SEARCH_URL, method = RequestMethod.GET)
    public ResponseEntity<?> searchIdeaTitle(@RequestParam("srchStr") String srchStr,
            HttpServletRequest req) {

        HttpSession ses = req.getSession();
        String token = (String) ses.getAttribute("userToken");
        Integer userId = (Integer) ses.getAttribute("userId");
        if (!sec.checkAccess(token, userId)) {

            return new ResponseEntity("ACCESS DENIED", HttpStatus.FORBIDDEN);
        }

        Gson gson = new Gson();
        List<Idea> results = ideaDao.searchIdeaTitle(srchStr, userId);
        Map<Integer, String> ideaMap = new HashMap<Integer, String>();
        for (Idea idea : results) {
            ideaMap.put(idea.getIdeaId(), idea.getIdeaTitle());
        }
        String resultsJson = gson.toJson(ideaMap);

        return new ResponseEntity(resultsJson, HttpStatus.OK);
    }

    /**
     * Reassigns the feature's ideaId to that that of the passed idea if they do
     * not match
     *
     * @param featList
     * @param idea
     * @return
     */
    private List<Feature> validateFeatureToIdea(List<Feature> featList, Idea idea) {
        List<Feature> validatedList = new ArrayList<Feature>();

        for (Feature feat : featList) {
            if (!idea.getIdeaId().equals(feat.getIdeaId()) || feat.getIdeaId() == null) {
                feat.setIdeaId(idea.getIdeaId());
            }
            if (feat.getFeatureId() == null) {
                feat.setFeatureId(0);
            }
            validatedList.add(feat);
        }

        return validatedList;
    }

    /**
     * Loads a new Idea into the model
     */
    @ModelAttribute(NEW_IDEA)
    private Idea loadNewIdea() {
        return new Idea();
    }
}
