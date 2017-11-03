package com.expercise.controller;

import com.expercise.domain.challenge.Challenge;
import com.expercise.service.challenge.ChallengeService;
import com.expercise.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StartCodingController {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private UserService userService;

    @RequestMapping("/start-coding")
    public ModelAndView listTagsWithChallengeCounts() {
        if (userService.isNewbie()) {
            Challenge defaultChallenge = challengeService.getDefaultChallenge();
            if (defaultChallenge != null) {
                return new ModelAndView("redirect:/challenges/" + defaultChallenge.getId() + "?newMember");
            }
        }

        ModelAndView modelAndView = new ModelAndView("tag/tagsList");

        // TODO ufuk: complete

        return modelAndView;
    }

}
