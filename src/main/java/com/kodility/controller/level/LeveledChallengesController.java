package com.kodility.controller.level;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.level.Level;
import com.kodility.service.challenge.LevelService;
import com.kodility.service.challenge.SolutionCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class LeveledChallengesController {

    @Autowired
    private LevelService levelService;

    @Autowired
    private SolutionCountService solutionCountService;

    @RequestMapping("/challenges")
    public ModelAndView listChallenges() {
        ModelAndView modelAndView = new ModelAndView("level/leveledChallenges");
        List<Level> levelList = levelService.getAllLevelsInOrder();
        modelAndView.addObject("levelList", levelList);
        modelAndView.addObject("solutionCountMap", prepareSolutionCountMapFor(levelList));
        modelAndView.addObject("currentLevelModel", levelService.getCurrentLevelModelOfCurrentUser());
        return modelAndView;
    }

    private Map<Challenge, Long> prepareSolutionCountMapFor(List<Level> levelList) {
        List<Challenge> challenges = new ArrayList<>();
        levelList.forEach(l -> challenges.addAll(l.getApprovedChallenges()));
        return solutionCountService.prepareSolutionCountMapFor(challenges);
    }

}
