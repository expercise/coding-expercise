package com.expercise.controller.theme;

import com.expercise.controller.RedirectUtils;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.level.Level;
import com.expercise.domain.theme.Theme;
import com.expercise.service.challenge.ChallengeService;
import com.expercise.service.challenge.SolutionCountService;
import com.expercise.service.challenge.SolutionService;
import com.expercise.service.level.LevelService;
import com.expercise.service.theme.ThemeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ThemedLevelsController {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private SolutionCountService solutionCountService;

    @Autowired
    private SolutionService solutionService;

    @RequestMapping("/themes/{themeId}/{bookmarkableThemeName}")
    public ModelAndView listThemedLevels(@PathVariable Long themeId, @PathVariable String bookmarkableThemeName) {
        Theme theme = themeService.findById(themeId);

        if (theme == null) {
            return RedirectUtils.redirect404();
        }

        if (theme.isBookmarkableNameChanged(bookmarkableThemeName)) {
            return RedirectUtils.redirectTo(theme.getBookmarkableUrl());
        }

        ModelAndView modelAndView = new ModelAndView("theme/levelList");
        modelAndView.addObject("theme", theme);

        List<Level> levelList = theme.getOrderedLevels();
        modelAndView.addObject("levelList", levelList);
        modelAndView.addObject("solutionCountMap", prepareSolutionCountMapForLevels(levelList));
        modelAndView.addObject("currentLevelModel", levelService.getCurrentLevelModelOfCurrentUserFor(theme));

        return modelAndView;
    }

    @RequestMapping(Theme.URL_FOR_NOT_THEMED_CHALLENGES)
    public ModelAndView listNotThemedChallenges() {
        ModelAndView modelAndView = new ModelAndView("theme/notThemedChallenges");

        List<Challenge> challengeList = challengeService.findNotThemedApprovedChallenges();
        modelAndView.addObject("challengeList", challengeList);
        modelAndView.addObject("solutionCountMap", prepareSolutionCountMapForChallenges(challengeList));
        modelAndView.addObject("solvedChallengesByCurrentUser", solutionService.getSolvedChallengesOfCurrentUser());

        return modelAndView;
    }

    private Map<Challenge, Long> prepareSolutionCountMapForLevels(List<Level> levelList) {
        List<Challenge> challenges = levelList.stream()
                .flatMap(l -> l.getApprovedChallenges().stream())
                .collect(Collectors.toList());

        return prepareSolutionCountMapForChallenges(challenges);
    }

    private Map<Challenge, Long> prepareSolutionCountMapForChallenges(List<Challenge> challenges) {
        return solutionCountService.prepareSolutionCountMapFor(challenges);
    }

}
