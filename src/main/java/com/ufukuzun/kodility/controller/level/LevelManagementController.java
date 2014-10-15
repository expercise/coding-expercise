package com.ufukuzun.kodility.controller.level;

import com.ufukuzun.kodility.controller.level.model.LevelModel;
import com.ufukuzun.kodility.controller.level.model.SaveLevelAjaxRequest;
import com.ufukuzun.kodility.service.challenge.LevelService;
import com.ufukuzun.myth.dialect.handler.annotation.AjaxRequestBody;
import com.ufukuzun.myth.dialect.handler.annotation.AjaxResponseBody;
import com.ufukuzun.myth.dialect.model.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/manage/levels")
public class LevelManagementController {

    @Autowired
    private LevelService levelService;

    @RequestMapping
    public ModelAndView levelManagement() {
        return getModelAndView();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @AjaxResponseBody
    public AjaxResponse saveLevel(@AjaxRequestBody SaveLevelAjaxRequest ajaxRequest) {
        ModelAndView modelAndView = getModelAndView();
        return new AjaxResponse(ajaxRequest, modelAndView);
    }

    private ModelAndView getModelAndView() {
        ModelAndView modelAndView = new ModelAndView("level/levelManagement");
        modelAndView.addObject("level", new LevelModel());
        modelAndView.addObject("levels", levelService.getAllLevelsInOrder());
        return modelAndView;
    }

}
