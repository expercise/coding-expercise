package com.kodility.controller.challenge;

import com.kodility.controller.RedirectUtil;
import com.kodility.controller.challenge.model.SolutionFromUser;
import com.kodility.domain.challenge.Challenge;
import com.kodility.enums.ProgrammingLanguage;
import com.kodility.service.challenge.ChallengeDisplayRule;
import com.kodility.service.challenge.ChallengeService;
import com.kodility.service.challenge.SolutionService;
import com.kodility.service.challenge.SolutionValidationService;
import com.kodility.service.challenge.model.SolutionValidationResult;
import com.kodility.utils.JsonUtils;
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