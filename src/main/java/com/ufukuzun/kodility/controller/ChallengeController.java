package com.ufukuzun.kodility.controller;

import com.ufukuzun.kodility.interpreter.javascript.JavaScriptInterpreter;
import com.ufukuzun.kodility.interpreter.python.JythonInterpreter;
import com.ufukuzun.kodility.model.Language;
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
    private JythonInterpreter jythonInterpreter;

    @RequestMapping(value = "/{challengeId}", method = RequestMethod.GET)
    public ModelAndView challengePage(@PathVariable Long challengeId) {
        ModelAndView modelAndView = new ModelAndView("challenge");
        modelAndView.addObject("solutionTemplate", "function solution(a, b) {\n    // solve\n}");
        modelAndView.addObject("languages", Language.values());
        return modelAndView;
    }

    @RequestMapping(value = "/eval/{language}", method = RequestMethod.POST)
    public @ResponseBody String challengePage(@RequestBody String solution, @PathVariable String language) {
        String solutionWithTest = solution + "; solution(1, 3);";

        Language langForEval = Language.getLanguage(language);

        if (langForEval == Language.JavaScript) {
            return javaScriptInterpreter.interpret(solutionWithTest).toString();
        } else if (langForEval == Language.Python) {
            return jythonInterpreter.interpret(solution, "solution(5,6)");
        }

        return "nop";
    }

}