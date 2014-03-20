package com.ufukuzun.kodility.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/challenges")
public class ChallengeController {

    @RequestMapping("/{challengeId}")
    public ModelAndView challengePage(@PathVariable Long challengeId) {
        return new ModelAndView("challenge");
    }

}