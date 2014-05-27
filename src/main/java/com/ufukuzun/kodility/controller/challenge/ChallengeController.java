package com.ufukuzun.kodility.controller.challenge;

import com.ufukuzun.kodility.controller.RedirectUtil;
import com.ufukuzun.kodility.controller.challenge.model.SolutionFromUser;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.service.challenge.ChallengeService;
import com.ufukuzun.kodility.service.challenge.SolutionValidationService;
import com.ufukuzun.kodility.service.challenge.model.SolutionValidationResult;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import com.ufukuzun.kodility.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private SolutionValidationService solutionValidationService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping
    public ModelAndView listChallenges() {
        ModelAndView modelAndView = new ModelAndView("challenge/challengeList");
        List<Challenge> challenges = challengeService.findAllApproved();

        modelAndView.addObject("challenges", challenges);

        return modelAndView;
    }

    @RequestMapping(value = "/{challengeId}", method = RequestMethod.GET)
    public ModelAndView challengePage(@PathVariable("challengeId") long challengeId) {
        Challenge challenge = challengeService.findById(challengeId);
        if (isChallengeNotAvailableToShow(challenge)) {
            return RedirectUtil.redirect404();
        }

        ModelAndView modelAndView = new ModelAndView("challenge/challenge");
        modelAndView.addObject("challenge", challenge);
        modelAndView.addObject("programmingLanguages", ProgrammingLanguage.values());
        modelAndView.addObject("solutionSignatures", JsonUtils.toJsonString(challengeService.prepareSignaturesMapFor(challenge)));

        return modelAndView;
    }

    @RequestMapping(value = "/eval", method = RequestMethod.POST)
    @ResponseBody
    public SolutionValidationResult evaluate(@RequestBody SolutionFromUser solutionFromUser) {
        return solutionValidationService.validateSolution(solutionFromUser);
    }

    private boolean isChallengeNotAvailableToShow(Challenge challenge) {
        User currentUser = authenticationService.getCurrentUser();
        return challenge == null || (challenge.isNotApproved() && !currentUser.equals(challenge.getUser()) && currentUser.isNotAdmin());
    }

}