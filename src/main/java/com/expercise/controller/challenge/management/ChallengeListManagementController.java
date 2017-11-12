package com.expercise.controller.challenge.management;

import com.expercise.controller.BaseManagementController;
import com.expercise.domain.challenge.Challenge;
import com.expercise.enums.ChallengeListingMode;
import com.expercise.service.challenge.ChallengeService;
import com.expercise.service.challenge.SolutionCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ChallengeListManagementController extends BaseManagementController {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private SolutionCountService solutionCountService;

    @GetMapping("/challenges")
    public ModelAndView listChallengesForAdmin() {
        List<Challenge> challenges = challengeService.findAll();
        ModelAndView modelAndView = new ModelAndView("challenge/challengeList");
        modelAndView.addObject("challenges", challenges);
        modelAndView.addObject("solutionCountMap", solutionCountService.prepareSolutionCountMapFor(challenges));
        modelAndView.addObject("mode", ChallengeListingMode.Admin.name());
        return modelAndView;
    }

}
