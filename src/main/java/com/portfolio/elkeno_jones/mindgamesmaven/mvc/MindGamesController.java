package com.portfolio.elkeno_jones.mindgamesmaven.mvc;

import com.portfolio.elkeno_jones.mindgamesmaven.dao.FeatureDao;
import com.portfolio.elkeno_jones.mindgamesmaven.dao.IdeaDao;
import com.portfolio.elkeno_jones.mindgamesmaven.dao.UserDao;
import com.portfolio.elkeno_jones.mindgamesmaven.model.Feature;
import com.portfolio.elkeno_jones.mindgamesmaven.model.Idea;
import com.portfolio.elkeno_jones.mindgamesmaven.model.IdeaWithFeatures;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
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

    private static final String IDEA_WRAPPER = "ideaWrapper";
    private static final String NEW_IDEA = "newIdea";
    private static final String IDEA_LIST = "ideaList";
    
    /* Idea hub view */
    private static final String IDEA_HUB_URL = "/ideaHub";
    private static final String IDEA_HUB_VIEW = "ideaHub";
    
    /* Save*/
    private static final String SAVE_URL = "/save";
    
    /* Develop Idea */
    private static final String DEVELOP_IDEA_VIEW = "developIdea";
    private static final String DEVELOP_NEW_IDEA_URL = "/develop/newIdea";
    private static final String DEVELOP_IDEA_URL = "/develop";
    private static final String ADD_FEATURE_URL = "/develop/add";
    
    /* Redirect and error*/
    private static final String ERROR_SAVING_VIEW = "error";
    private static final String REDIRECT_VIEW = "/redirect";

    @RequestMapping(value = IDEA_HUB_URL)
    public String home(Model model) {
        Idea newIdea = new Idea();
        List<Idea> userIdeas = ideaDao.getIdeasByUserId(1); //TODO replace with list of ideas by a user
        List<IdeaWithFeatures> ideaList = new ArrayList<IdeaWithFeatures>();
        
        for(Idea tempIdea : userIdeas){
            IdeaWithFeatures ideaWrapper = new IdeaWithFeatures();
            List<Feature> features = featureDao.getFeaturesByIdeaId(tempIdea.getIdeaId());
            ideaWrapper.setIdea(tempIdea);
            ideaWrapper.setFeatures(features);
            
            ideaList.add(ideaWrapper);
        }
        
        model.addAttribute("ideaList", ideaList);
        model.addAttribute("newIdea", newIdea);
        
        return IDEA_HUB_VIEW;
    }

    @RequestMapping(value = DEVELOP_IDEA_URL, method = RequestMethod.POST)
    public String developIdea(Model model, @RequestParam String ideaId) {
        Integer id;
        try {
            id = Integer.parseInt(ideaId);
            IdeaWithFeatures ideaWrapper = new IdeaWithFeatures();
            Idea newIdea = ideaDao.getIdeaById(id);
            List<Feature> features = featureDao.getFeaturesByIdeaId(id);

            ideaWrapper.setFeatures(features);
            ideaWrapper.setIdea(newIdea);
            ideaWrapper.setNewFeature(new Feature());
            
            model.addAttribute("ideaWrapper", ideaWrapper);
        } catch (NumberFormatException nfe) {
            String errMessage ="[ Invalid id type - developIdea ] " + nfe.getMessage();
            model.addAttribute("errMessage", errMessage);
//            throw new InputMismatchException("[ Invalid id type ] " + nfe.getMessage());
        } catch (HibernateException he){
            String errMessage ="[ Hibernate error - developIdea ] " + he.getMessage();
            model.addAttribute("errMessage", errMessage);//do something
        } catch (Exception e){
            String errMessage ="[ Java Exception - developIdea ] " + e.getMessage();
            model.addAttribute("errMessage", errMessage);
        }
        
        return DEVELOP_IDEA_VIEW;
    }

    @RequestMapping(value = ADD_FEATURE_URL, method = RequestMethod.POST)
    public String addFeature(Model model,
            @ModelAttribute("ideaWrapper") IdeaWithFeatures ideaWrapper) {

        Feature newFeat = ideaWrapper.getNewFeature();
        ideaWrapper.getFeatures().add(newFeat);
        ideaWrapper.setNewFeature(new Feature());

        model.addAttribute("ideaWrapper", ideaWrapper);

        return DEVELOP_IDEA_VIEW;
    }
    
    @RequestMapping(value = SAVE_URL, method = RequestMethod.GET)
    public String saveIdea(Model model, @ModelAttribute("ideaWrapper") IdeaWithFeatures ideaWrapper){
        boolean saveSuccessful;
        boolean ideaSaveSuccessful = false;
        String redirectUrl = "";
        try{
            try{
                if(ideaWrapper.getIdea().getUserId() == null){
                    ideaWrapper.getIdea().setUserId(1);
                }
                
                ideaWrapper.setIdea(
                        ideaDao.saveNewIdea(ideaWrapper.getIdea())
                );
                ideaSaveSuccessful = true;
            } catch (HibernateException he){
                /* TODO: handle exception */
            }

            List<Feature> correctedFeatures = 
                    validateFeatureToIdea(ideaWrapper.getFeatures(), ideaWrapper.getIdea());
            saveSuccessful = true;
            for(Feature feature : correctedFeatures){
                if(!featureDao.saveFeature(feature)){
                    saveSuccessful = false;
                }
            }
            if(saveSuccessful && ideaSaveSuccessful){
                String location = "/MindGamesMaven";
                model.addAttribute("redirectUrl", location + IDEA_HUB_URL);
                redirectUrl = REDIRECT_VIEW;
            } else {
                redirectUrl = ERROR_SAVING_VIEW;
                String errMessage = "Failed to save idea and features";
                model.addAttribute("errMessage", errMessage);
            }
        } catch (Exception e){
            /* TODO: handle exception */
        }
        return redirectUrl;
    }
    
    @RequestMapping(value = DEVELOP_NEW_IDEA_URL, method = RequestMethod.POST)
    public String developNewIdea(Model model, @ModelAttribute("newIdea") Idea idea) {
        try {
            idea.setIdeaId(0);
            
            IdeaWithFeatures ideaWrapper = new IdeaWithFeatures();
            List<Feature> features = new ArrayList<Feature>();

            ideaWrapper.setFeatures(features);
            ideaWrapper.setIdea(idea);
            ideaWrapper.setNewFeature(new Feature());
            
            model.addAttribute("ideaWrapper", ideaWrapper);
        } catch (HibernateException he){
            String errMessage ="[ Hibernate error - developNewIdea] " + he.getMessage();
            model.addAttribute("errMessage", errMessage);//do something
        } catch (Exception e){
            String errMessage ="[ Java Exception - developNewIdea] " + e.getMessage();
            model.addAttribute("errMessage", errMessage);
        }
        
        return DEVELOP_IDEA_VIEW;
    }
    
    @RequestMapping(value = REDIRECT_VIEW)
    public String redirect(Model model, 
            @ModelAttribute("redirectUrl") String redirect){
        
        model.addAttribute("redirectUrl", redirect);
        
        return REDIRECT_VIEW;
    }
    
    /**
     * Reassigns the feature's ideaId to that that of the passed idea if they
     * do not match
     * 
     * @param featList
     * @param idea
     * @return 
     */
    private List<Feature> validateFeatureToIdea(List<Feature> featList, Idea idea){
        List<Feature> validatedList = new ArrayList<Feature>();
        
        for(Feature feat : featList){
            if(!idea.getIdeaId().equals(feat.getIdeaId()) || feat.getIdeaId() == null){
                feat.setIdeaId(idea.getIdeaId());
            }
            if(feat.getFeatureId() == null){
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
    private Idea loadNewIdea(){
        return new Idea();
    }
}
