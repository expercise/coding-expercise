package com.expercise.controller.level.management;

import com.expercise.controller.BaseManagementController;
import com.expercise.controller.RedirectUtils;
import com.expercise.controller.level.model.LevelModel;
import com.expercise.domain.level.Level;
import com.expercise.service.level.LevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LevelManagementController extends BaseManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LevelManagementController.class);

    @Autowired
    private LevelService levelService;

    @GetMapping("/levels")
    public ModelAndView levelManagement(ModelAndView modelAndView) {
        initializeModelAndView(modelAndView);
        modelAndView.addObject("level", new LevelModel());
        return modelAndView;
    }

    @PostMapping("/levels")
    public ModelAndView saveLevel(@ModelAttribute("level") @Valid LevelModel levelModel, BindingResult bindingResult, ModelAndView modelAndView) {
        initializeModelAndView(modelAndView);

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        levelService.saveNewLevelOrUpdate(levelModel.toLevel());

        return RedirectUtils.redirectTo("/manage/levels");
    }

    // TODO ufuk: validate before delete
    @GetMapping("/levels/delete/{levelId}")
    public ModelAndView deleteLevel(@PathVariable long levelId) {
        try {
            Level levelToDelete = levelService.findById(levelId);
            levelService.delete(levelToDelete);
        } catch (Exception e) {
            LOGGER.error("Deleting level is failed: " + levelId, e);
        }
        return RedirectUtils.redirectTo("/manage/levels");
    }

    private void initializeModelAndView(ModelAndView modelAndView) {
        modelAndView.setViewName("level/levelManagement");
        modelAndView.addObject("levels", levelService.getAllLevelsInOrder());
    }
}
