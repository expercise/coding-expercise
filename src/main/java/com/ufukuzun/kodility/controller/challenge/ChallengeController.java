package com.ufukuzun.kodility.controller.challenge;

import com.ufukuzun.kodility.controller.challenge.model.SolutionFromUser;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.service.challenge.ChallengeService;
import com.ufukuzun.kodility.service.challenge.SolutionValidationService;
import com.ufukuzun.kodility.service.challenge.model.SolutionValidationResult;
import com.ufukuzun.kodility.utils.JsonUtils;
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

    @RequestMapping(value = "/{challengeId}", method = RequestMethod.GET)
    public ModelAndView challengePage(@PathVariable String challengeId) {
        ModelAndView modelAndView = new ModelAndView("challenge");

        Challenge challenge = challengeService.findById(challengeId);
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

}