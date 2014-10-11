package com.ufukuzun.kodility.controller.level;

import com.ufukuzun.kodility.service.challenge.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LevelManagementController {

    @Autowired
    private LevelService levelService;

    @RequestMapping("/manage/levels")
    public ModelAndView levelManagement() {
        return new ModelAndView("level/levelManagement").addObject("levels", levelService.getAllLevelsInOrder());
    }

}
