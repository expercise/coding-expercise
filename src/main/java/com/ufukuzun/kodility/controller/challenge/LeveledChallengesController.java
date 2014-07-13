package com.ufukuzun.kodility.controller.challenge;

import com.ufukuzun.kodility.service.challenge.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LeveledChallengesController {

    @Autowired
    private LevelService levelService;

    @RequestMapping("/challenges")
    public ModelAndView listChallenges() {
        ModelAndView modelAndView = new ModelAndView("challenge/leveledChallenges");
        modelAndView.addObject("levelList", levelService.getAllLevels());
        return modelAndView;
    }

}
