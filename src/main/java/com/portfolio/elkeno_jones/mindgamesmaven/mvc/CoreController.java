/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.elkeno_jones.mindgamesmaven.mvc;

import com.portfolio.elkeno_jones.mindgamesmaven.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Elkeno
 */
@Controller
public class CoreController {
    protected static final String LOGIN = "login";
    protected static final String AUTHENTICATE_URL = "/auth";
    
    @RequestMapping(value = AUTHENTICATE_URL)
    public String setLanding(Model model){
        String landingPage = "";
        
        landingPage = LOGIN;
        model.addAttribute("user", new User());
        return landingPage;
    } 
}
