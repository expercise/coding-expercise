package com.expercise.controller.challenge;

import com.expercise.controller.RedirectUtils;
import com.expercise.controller.challenge.model.ChallengeResetModel;
import com.expercise.controller.challenge.model.SolutionFromUser;
import com.expercise.domain.challenge.Challenge;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.TestCasesWithSourceModel;
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
    public ModelAndView challengePage(@PathVariable long challengeId) {
        Challenge challenge = challengeService.findById(challengeId);
        if (challenge == null) {
            return RedirectUtils.redirect404();
        } else if (challengeDisplayRule.isNotDisplayable(challenge)) {
            return RedirectUtils.redirect403();
        }

        ModelAndView modelAndView = new ModelAndView("challenge/challenge");
        modelAndView.addObject("challenge", challenge);
        modelAndView.addObject("programmingLanguages", ProgrammingLanguage.values());
        modelAndView.addObject("solutionSignatures", JsonUtils.toJsonString(challengeService.prepareSignaturesMapFor(challenge)));
        modelAndView.addObject("userSolutions", JsonUtils.toJsonString(solutionService.getUserSolutionModels(challenge)));
        //todo:batu get state for other languages later
        modelAndView.addObject("testCasesWithSource", JsonUtils.toJsonString(challengeService.getUserStateFor(challenge, ProgrammingLanguage.JavaScript)));

        return modelAndView;
    }

    @RequestMapping(value = "/eval", method = RequestMethod.POST)
    @ResponseBody
    public SolutionValidationResult evaluate(@RequestBody SolutionFromUser solutionFromUser) {
        return solutionValidationService.validateSolution(solutionFromUser);
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    @ResponseBody
    public TestCasesWithSourceModel reset(@RequestBody ChallengeResetModel challengeResetModel) {
        Challenge challenge = challengeService.findById(challengeResetModel.getChallengeId());
        ProgrammingLanguage programmingLanguage = ProgrammingLanguage.getLanguage(challengeResetModel.getLanguage()).get();
        return challengeService.resetUserStateFor(challenge, programmingLanguage);
    }

}