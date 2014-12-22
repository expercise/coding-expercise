package com.kodility.controller.theme;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.level.Level;
import com.kodility.domain.theme.Theme;
import com.kodility.service.challenge.SolutionCountService;
import com.kodility.service.level.LevelService;
import com.kodility.service.theme.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ThemedLevelsController {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private SolutionCountService solutionCountService;

    @RequestMapping("/themes/{themeId}")
    public ModelAndView listThemedLevels(@PathVariable("themeId") Long themeId) {
        ModelAndView modelAndView = new ModelAndView("theme/levelList");

        Theme theme = themeService.findById(themeId);
        modelAndView.addObject("theme", theme);

        List<Level> levelList = theme.getOrderedLevels();
        modelAndView.addObject("levelList", levelList);
        modelAndView.addObject("solutionCountMap", prepareSolutionCountMapFor(levelList));
        modelAndView.addObject("currentLevelModel", levelService.getCurrentLevelModelOfCurrentUserFor(theme));

        return modelAndView;
    }

    private Map<Challenge, Long> prepareSolutionCountMapFor(List<Level> levelList) {
        List<Challenge> challenges = new ArrayList<>();
        levelList.forEach(l -> challenges.addAll(l.getApprovedChallenges()));
        return solutionCountService.prepareSolutionCountMapFor(challenges);
    }

}
