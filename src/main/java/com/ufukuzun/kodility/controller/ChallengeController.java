package com.ufukuzun.kodility.controller;

import com.ufukuzun.kodility.interpreter.javascript.JavaScriptInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    private JavaScriptInterpreter javaScriptInterpreter;

    @RequestMapping("/{challengeId}")
    public ModelAndView challengePage(@PathVariable Long challengeId) {
        ModelAndView modelAndView = new ModelAndView("challenge");
        modelAndView.addObject("solutionTemplate", "function solution(a, b) {\n    // solve\n}");
        return modelAndView;
    }

    @RequestMapping(value = "/eval", method = RequestMethod.POST)
    public @ResponseBody String challengePage(@RequestBody String solution) {
        solution += solution + "; solution(1, 3);";
        return javaScriptInterpreter.interpret(solution).toString();
    }

}