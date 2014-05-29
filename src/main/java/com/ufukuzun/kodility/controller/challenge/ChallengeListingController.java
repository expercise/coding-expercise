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

        String mode = ChallengeListingMode.ForAll.name();
        ModelAndView modelAndView = prepareModelAndViewForListing(challenges, mode);

        return modelAndView;
    }

    @RequestMapping("/challenges/myChallenges")
    public ModelAndView listChallengesOfUser() {
        List<Challenge> challenges = challengeService.findAllChallengesOfUser();

        String mode = ChallengeListingMode.User.name();
        ModelAndView modelAndView = prepareModelAndViewForListing(challenges, mode);

        return modelAndView;
    }

    @RequestMapping("/manage/challenges")
    public ModelAndView listChallengesForAdmin() {
        List<Challenge> challenges = challengeService.findAll();

        String mode = ChallengeListingMode.Admin.name();
        ModelAndView modelAndView = prepareModelAndViewForListing(challenges, mode);

        return modelAndView;
    }

    private ModelAndView prepareModelAndViewForListing(List<Challenge> challenges, String mode) {
        ModelAndView modelAndView = new ModelAndView("challenge/challengeList");
        modelAndView.addObject("challenges", challenges);
        modelAndView.addObject("mode", mode);
        return modelAndView;
    }

}
