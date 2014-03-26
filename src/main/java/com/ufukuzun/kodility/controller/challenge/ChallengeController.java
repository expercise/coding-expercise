package com.ufukuzun.kodility.controller.challenge;

import com.ufukuzun.kodility.controller.challenge.model.SolutionFromUser;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.interpreter.javascript.JavaScriptInterpreter;
import com.ufukuzun.kodility.interpreter.python.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    private JavaScriptInterpreter javaScriptInterpreter;

    @Autowired
    private PythonInterpreter pythonInterpreter;

    @RequestMapping(value = "/{challengeId}", method = RequestMethod.GET)
    public ModelAndView challengePage(@PathVariable Long challengeId) {
        ModelAndView modelAndView = new ModelAndView("challenge");
        Challenge sampleChallenge = SampleChallenges.sampleChallenges.get(0);
        modelAndView.addObject("challenge", sampleChallenge);
        modelAndView.addObject("selectedProgrammingLanguage", sampleChallenge.getProgrammingLanguages().get(0));
        return modelAndView;
    }

    @RequestMapping(value = "/eval", method = RequestMethod.POST)
    @ResponseBody
    public String evaluate(@ModelAttribute SolutionFromUser solutionFromUser) {
        // TODO ufuk: complete
        return "No Result";
    }

}