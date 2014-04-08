package com.ufukuzun.kodility.controller;

import com.ufukuzun.kodility.service.challenge.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ChallengeService challengeService;

    @RequestMapping
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("startingChallengeId", challengeService.findById("123").getId());
        return modelAndView;
    }

}
