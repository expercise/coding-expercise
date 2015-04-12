package com.expercise.controller.challenge;

import com.expercise.controller.RedirectUtils;
import com.expercise.controller.challenge.model.ChallengeModel;
import com.expercise.controller.challenge.model.SaveChallengeResponse;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.ChallengeType;
import com.expercise.domain.user.User;
import com.expercise.enums.DataType;
import com.expercise.enums.Lingo;
import com.expercise.enums.ManagementMode;
import com.expercise.service.challenge.ChallengeModelHelper;
import com.expercise.service.challenge.ChallengeService;
import com.expercise.service.level.LevelService;
import com.expercise.service.user.AuthenticationService;
import com.expercise.utils.JsonUtils;
import com.expercise.utils.validation.SaveChallengeValidator;
import com.expercise.utils.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/challenges")
public class ChallengeManagementController {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private SaveChallengeValidator saveChallengeValidator;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private ChallengeModelHelper challengeModelHelper;

    @RequestMapping(value = "/addChallenge", method = RequestMethod.GET)
    public ModelAndView addChallengePage() {
        return prepareChallengeManagementViewModel(ManagementMode.Add);
    }

    @RequestMapping(value = "/saveChallenge", method = RequestMethod.POST)
    @ResponseBody
    public SaveChallengeResponse saveChallenge(@RequestBody ChallengeModel challengeModel, BindingResult bindingResult) {
        saveChallengeValidator.validate(challengeModel, bindingResult);

        if (bindingResult.hasErrors()) {
            return SaveChallengeResponse.failedResponse(ValidationUtils.extractAllErrorCodes(bindingResult));
        }

        Long challengeId = challengeService.saveChallenge(challengeModel);

        return SaveChallengeResponse.successResponse(challengeId);
    }

    @RequestMapping(value = "/updateChallenge/{challengeId}", method = RequestMethod.GET)
    public ModelAndView updateChallengePage(@PathVariable long challengeId) {

        Challenge challenge = challengeService.findById(challengeId);

        if (isCurrentUserNotAuthorOrAdmin(challenge)) {
            return RedirectUtils.redirectHome();
        }

        ModelAndView modelAndView = prepareChallengeManagementViewModel(ManagementMode.Update);
        modelAndView.addObject("challengeModel", JsonUtils.toJsonString(challengeModelHelper.createModelFrom(challenge)));

        return modelAndView;
    }

    private ModelAndView prepareChallengeManagementViewModel(ManagementMode managementMode) {
        ModelAndView modelAndView = new ModelAndView("challenge/manageChallenge");
        modelAndView.addObject("mode", managementMode);
        modelAndView.addObject("lingos", Lingo.values());
        modelAndView.addObject("dataTypes", DataType.values());
        modelAndView.addObject("challengeTypes", ChallengeType.values());
        modelAndView.addObject("levels", levelService.getAllLevelsInOrder());
        return modelAndView;
    }

    private boolean isCurrentUserNotAuthorOrAdmin(Challenge challenge) {
        User currentUser = authenticationService.getCurrentUser();
        return !currentUser.equals(challenge.getUser()) && currentUser.isNotAdmin();
    }

}