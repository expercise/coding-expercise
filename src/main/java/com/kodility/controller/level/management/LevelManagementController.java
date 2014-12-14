package com.kodility.controller.level.management;

import com.kodility.controller.BaseManagementController;
import com.kodility.controller.level.model.SaveLevelAjaxRequest;
import com.kodility.service.level.LevelService;
import com.ufukuzun.myth.dialect.handler.annotation.AjaxRequestBody;
import com.ufukuzun.myth.dialect.handler.annotation.AjaxResponseBody;
import com.ufukuzun.myth.dialect.model.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LevelManagementController extends BaseManagementController {

    @Autowired
    private LevelService levelService;

    @RequestMapping("/levels")
    public ModelAndView levelManagement() {
        return getModelAndView(new SaveLevelAjaxRequest.LevelModel());
    }

    @RequestMapping(value = "/levels/save", method = RequestMethod.POST)
    @AjaxResponseBody
    public AjaxResponse saveLevel(@AjaxRequestBody(validate = true, targetName = "level") SaveLevelAjaxRequest ajaxRequest) {
        ModelAndView modelAndView;
        if (ajaxRequest.isModelValid()) {
            levelService.save(ajaxRequest.getModel().toLevel());
            modelAndView = getModelAndView(new SaveLevelAjaxRequest.LevelModel());
        } else {
            modelAndView = getModelAndView(ajaxRequest.getModel());
        }
        return new AjaxResponse(ajaxRequest, modelAndView);
    }

    private ModelAndView getModelAndView(SaveLevelAjaxRequest.LevelModel levelModel) {
        ModelAndView modelAndView = new ModelAndView("level/levelManagement");
        modelAndView.addObject("level", levelModel);
        modelAndView.addObject("levels", levelService.getAllLevelsInOrder());
        return modelAndView;
    }

}
