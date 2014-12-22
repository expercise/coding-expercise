package com.kodility.controller.theme;

import com.kodility.service.theme.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ThemesController {

    @Autowired
    private ThemeService themeService;

    @RequestMapping("/themes")
    public ModelAndView listThemes() {
        ModelAndView modelAndView = new ModelAndView("theme/themeList");
        modelAndView.addObject("themeList", themeService.getAll());
        return modelAndView;
    }

}
