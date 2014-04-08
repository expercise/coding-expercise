package com.ufukuzun.kodility.controller.challenge;

import com.ufukuzun.kodility.controller.challenge.model.SolutionFromUser;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.service.challenge.ChallengeService;
import com.ufukuzun.kodility.service.challenge.SolutionValidationService;
import com.ufukuzun.kodility.service.challenge.model.SolutionValidationResult;
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

        modelAndView.addObject("solutionTemplate", "function solution() {\n}");
        modelAndView.addObject("programmingLanguages", ProgrammingLanguage.values());

        return modelAndView;
    }

    @RequestMapping(value = "/eval", method = RequestMethod.POST)
    @ResponseBody
    public SolutionValidationResult evaluate(@RequestBody SolutionFromUser solutionFromUser) {
        return solutionValidationService.validateSolution(solutionFromUser);
    }

    @RequestMapping(value = "/changeLanguage", method = RequestMethod.POST)
    @ResponseBody
    public String changeLanguage(@RequestBody LanguageChoice languageChoice) {
        ProgrammingLanguage programmingLanguage = ProgrammingLanguage.getLanguage(languageChoice.getLanguage());

        Challenge challenge = challengeService.findById(languageChoice.getChallengeId());

        if (programmingLanguage == ProgrammingLanguage.Python) {
            return generateCallPatternForPython(challenge);
        }
        return generateCallPatternForJavaScript(challenge);
    }

    private String generateCallPatternForPython(Challenge challenge) {
        int inputSize = challenge.getInputTypes().size();
        String[] letters = new String[] {"a", "b", "c", "d"};
        String solutionTemplate;
        solutionTemplate = "def solution(";
        for (int i = 0; i < inputSize; i++) {
            solutionTemplate += letters[i];
            if (i != inputSize - 1) {
                solutionTemplate += ", ";
            }
        }

        solutionTemplate += "):\n";
        return solutionTemplate;
    }

    private String generateCallPatternForJavaScript(Challenge challenge) {
        int inputSize = challenge.getInputTypes().size();
        String[] letters = new String[] {"a", "b", "c", "d"};
        String solutionTemplate;
        solutionTemplate = "function solution(";
        for (int i = 0; i < inputSize; i++) {
            solutionTemplate += letters[i];
            if (i != inputSize - 1) {
                solutionTemplate += ", ";
            }
        }

        solutionTemplate += "){\n}";
        return solutionTemplate;
    }

}