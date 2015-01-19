package com.kodility.controller.level.management;

import com.kodility.controller.BaseManagementController;
import com.kodility.controller.level.model.SaveLevelAjaxRequest;
import com.kodility.domain.level.Level;
import com.kodility.service.level.LevelService;
import com.ufukuzun.myth.dialect.handler.annotation.AjaxRequestBody;
import com.ufukuzun.myth.dialect.handler.annotation.AjaxResponseBody;
import com.ufukuzun.myth.dialect.model.AjaxRequest;
import com.ufukuzun.myth.dialect.model.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static com.kodility.controller.level.model.SaveLevelAjaxRequest.LevelModel;

@Controller
public class LevelManagementController extends BaseManagementController {

    @Autowired
    private LevelService levelService;

    @RequestMapping("/levels")
    public ModelAndView levelManagement() {
        return getModelAndView();
    }

    @RequestMapping(value = "/levels/save", method = RequestMethod.POST)
    @AjaxResponseBody
    public AjaxResponse saveLevel(@AjaxRequestBody(validate = true, targetName = "level") SaveLevelAjaxRequest ajaxRequest) {
        ModelAndView modelAndView;
        if (ajaxRequest.isModelValid()) {
            levelService.saveNewLevelOrUpdate(ajaxRequest.getModel().toLevel());
            modelAndView = getModelAndView();
        } else {
            modelAndView = getModelAndView(ajaxRequest.getModel());
        }
        return new AjaxResponse(ajaxRequest, modelAndView);
    }

    // TODO ufuk: validate before delete etc.
    @RequestMapping(value = "/levels/delete/{levelId}", method = RequestMethod.POST)
    @AjaxResponseBody
    public AjaxResponse deleteLevel(@AjaxRequestBody AjaxRequest<?> ajaxRequest, @PathVariable long levelId) {
        Level levelToDelete = levelService.findById(levelId);
        levelService.delete(levelToDelete);
        return new AjaxResponse(ajaxRequest, getModelAndView());
    }

    private ModelAndView getModelAndView() {
        return getModelAndView(new LevelModel());
    }

    private ModelAndView getModelAndView(LevelModel levelModel) {
        ModelAndView modelAndView = new ModelAndView("level/levelManagement");
        modelAndView.addObject("levels", levelService.getAllLevelsInOrder());
        modelAndView.addObject("level", levelModel);
        return modelAndView;
    }

}
