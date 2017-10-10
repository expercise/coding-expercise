package com.expercise.controller.level.management;

import com.expercise.controller.BaseManagementController;
import com.expercise.controller.level.model.SaveLevelRequest;
import com.expercise.domain.level.Level;
import com.expercise.service.level.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LevelManagementController extends BaseManagementController {

    // TODO: implement from scratch without "myth"

    @Autowired
    private LevelService levelService;

    @RequestMapping("/levels")
    public ModelAndView levelManagement() {
        return null;
    }

    @RequestMapping(value = "/levels/save", method = RequestMethod.POST)
    public void saveLevel(SaveLevelRequest saveLevelRequest) {
        levelService.saveNewLevelOrUpdate(saveLevelRequest.toLevel());
    }

    // TODO ufuk: validate before delete etc.
    @RequestMapping(value = "/levels/delete/{levelId}", method = RequestMethod.POST)
    public void deleteLevel(SaveLevelRequest saveLevelRequest, @PathVariable long levelId) {
        Level levelToDelete = levelService.findById(levelId);
        levelService.delete(levelToDelete);
    }

}
