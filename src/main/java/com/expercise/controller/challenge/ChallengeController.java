package com.expercise.controller.challenge;

import com.expercise.controller.RedirectUtil;
import com.expercise.controller.challenge.model.SolutionFromUser;
import com.expercise.domain.challenge.Challenge;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.service.challenge.ChallengeDisplayRule;
import com.expercise.service.challenge.ChallengeService;
import com.expercise.service.challenge.SolutionService;
import com.expercise.service.challenge.SolutionValidationService;
import com.expercise.service.challenge.model.SolutionValidationResult;
import com.expercise.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private SolutionValidationService solutionValidationService;

    @Autowired
    private ChallengeDisplayRule challengeDisplayRule;

    @Autowired
    private SolutionService solutionService;

    @RequestMapping(value = "/{challengeId}", method = RequestMethod.GET)
    public ModelAndView challengePage(@PathVariable("challengeId") long challengeId) {
        Challenge challenge = challengeService.findById(challengeId);
        if (challengeDisplayRule.isNotDisplayable(challenge)) {
            return RedirectUtil.redirect404();
        }

        ModelAndView modelAndView = new ModelAndView("challenge/challenge");
        modelAndView.addObject("challenge", challenge);
        modelAndView.addObject("programmingLanguages", ProgrammingLanguage.values());
        modelAndView.addObject("solutionSignatures", JsonUtils.toJsonString(challengeService.prepareSignaturesMapFor(challenge)));
        modelAndView.addObject("userSolutions", JsonUtils.toJsonString(solutionService.getUserSolutionModels(challenge)));

        return modelAndView;
    }

    @RequestMapping(value = "/eval", method = RequestMethod.POST)
    @ResponseBody
    public SolutionValidationResult evaluate(@RequestBody SolutionFromUser solutionFromUser) {
        return solutionValidationService.validateSolution(solutionFromUser);
    }

}