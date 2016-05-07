package com.expercise.controller.theme;

import com.expercise.domain.theme.Theme;
import com.expercise.service.theme.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ThemesController {

    @Autowired
    private ThemeService themeService;

    @RequestMapping("/themes")
    public ModelAndView listThemes() {
        ModelAndView modelAndView = new ModelAndView("theme/themeList");
        List<Theme> themes = themeService.getAll();
        modelAndView.addObject("themeList", themes);
        modelAndView.addObject("challengeCountsMap", themeService.prepareChallengeCounts(themes));
        return modelAndView;
    }

}
