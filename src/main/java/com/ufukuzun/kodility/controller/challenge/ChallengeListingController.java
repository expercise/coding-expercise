package com.ufukuzun.kodility.controller.challenge;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ChallengeListingMode;
import com.ufukuzun.kodility.service.challenge.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ChallengeListingController {

    @Autowired
    private ChallengeService challengeService;

    @RequestMapping("/challenges")
    public ModelAndView listChallenges() {
        List<Challenge> challenges = challengeService.findAllApproved();
        return prepareModelAndViewForListing(challenges, ChallengeListingMode.ForAll.name());
    }

    @RequestMapping("/challenges/myChallenges")
    public ModelAndView listChallengesOfUser() {
        List<Challenge> challenges = challengeService.findAllChallengesOfUser();
        return prepareModelAndViewForListing(challenges, ChallengeListingMode.User.name());
    }

    @RequestMapping("/manage/challenges")
    public ModelAndView listChallengesForAdmin() {
        List<Challenge> challenges = challengeService.findAll();
        return prepareModelAndViewForListing(challenges, ChallengeListingMode.Admin.name());
    }

    private ModelAndView prepareModelAndViewForListing(List<Challenge> challenges, String mode) {
        ModelAndView modelAndView = new ModelAndView("challenge/challengeList");
        modelAndView.addObject("challenges", challenges);
        modelAndView.addObject("mode", mode);
        return modelAndView;
    }

}
