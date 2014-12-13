package com.kodility.controller.theme;

import com.kodility.domain.theme.Theme;
import com.kodility.enums.Lingo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ThemesController {

    @RequestMapping("/themes")
    public ModelAndView listChallenges() {
        ModelAndView modelAndView = new ModelAndView("theme/themeList");

        // TODO ufuk: remove this dummy themes and get from database
        List<Theme> themeList = new ArrayList<>();
        while (themeList.size() < 5) {
            Theme theme = new Theme();
            theme.setId(Long.valueOf(themeList.size() + 1));
            theme.setNames(new HashMap<Lingo, String>() {{
                put(Lingo.English, "Theme " + theme.getId());
                put(Lingo.Turkish, "Tema " + theme.getId());
            }});
            themeList.add(theme);
        }

        modelAndView.addObject("themeList", themeList);

        return modelAndView;
    }

}
