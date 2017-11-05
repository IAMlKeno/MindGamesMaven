package com.portfolio.elkeno_jones.mindgamesmaven.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuController {

    private static final String ABOUT_VIEW = "about";
    private static final String ABOUT_URL = "/about";

    private static final String HOW_TO_USE_URL = "/howtouse";
    private static final String HOW_TO_USE_VIEW = "howtouse";
    
    private static final String UPCOMING_FEATURES_URL = "/upcoming";
    private static final String UPCOMING_FEATURES_VIEW = "upcomingfeatures";
    
    protected static final String AUTHENTICATE_URL = "/auth";

    private static final String ERROR_VIEW = "error";
    private static final String REDIRECT_VIEW = "/redirect";

    @RequestMapping(value = ABOUT_URL)
    public String aboutPage(Model model) {
        String redirectUrl = ABOUT_VIEW;
        
        return redirectUrl;
    }
    
    @RequestMapping(value = HOW_TO_USE_URL)
    public String howToUsePage(Model model) {
        String redirectUrl = HOW_TO_USE_VIEW;
        
        return redirectUrl;
    }
    
    @RequestMapping(value = UPCOMING_FEATURES_URL)
    public String upcomingFeaturesPage(Model model) {
        String redirectUrl = UPCOMING_FEATURES_VIEW;
        
        return redirectUrl;
    }
}
