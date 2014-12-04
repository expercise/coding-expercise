package com.kodility.controller.challenge.management;

import com.kodility.controller.BaseManagementController;
import com.kodility.domain.challenge.Challenge;
import com.kodility.enums.ChallengeListingMode;
import com.kodility.service.challenge.ChallengeService;
import com.kodility.service.challenge.SolutionCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ChallengeListManagementController extends BaseManagementController {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private SolutionCountService solutionCountService;

    @RequestMapping("/challenges")
    public ModelAndView listChallengesForAdmin() {
        List<Challenge> challenges = challengeService.findAll();
        ModelAndView modelAndView = new ModelAndView("challenge/challengeList");
        modelAndView.addObject("challenges", challenges);
        modelAndView.addObject("solutionCountMap", solutionCountService.prepareSolutionCountMapFor(challenges));
        modelAndView.addObject("mode", ChallengeListingMode.Admin.name());
        return modelAndView;
    }

}
