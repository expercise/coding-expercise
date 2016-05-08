package com.expercise.controller.theme;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.theme.Theme;
import com.expercise.service.challenge.ChallengeService;
import com.expercise.service.theme.ThemeService;
import com.expercise.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ThemesController {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private UserService userService;

    @RequestMapping("/themes")
    public ModelAndView listThemes() {
        if (userService.isNewbie()) {
            Challenge defaultChallenge = challengeService.getDefaultChallenge();
            if (defaultChallenge != null) {
                return new ModelAndView("redirect:/challenges/" + defaultChallenge.getId() + "?newMember");
            }
        }

        ModelAndView modelAndView = new ModelAndView("theme/themeList");
        List<Theme> themes = themeService.getAll();
        modelAndView.addObject("themeList", themes);
        modelAndView.addObject("challengeCountsMap", themeService.prepareChallengeCounts(themes));
        return modelAndView;
    }

}
