package com.ufukuzun.kodility.controller.challenge;

import com.ufukuzun.kodility.controller.challenge.model.ChallengeModel;
import com.ufukuzun.kodility.controller.challenge.model.SaveChallengeResponse;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.enums.Lingo;
import com.ufukuzun.kodility.enums.ManagementMode;
import com.ufukuzun.kodility.service.challenge.ChallengeService;
import com.ufukuzun.kodility.utils.JsonUtils;
import com.ufukuzun.kodility.utils.validation.SaveChallengeValidator;
import com.ufukuzun.kodility.utils.validation.ValidationUtils;
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

    @RequestMapping(value = "/addChallenge", method = RequestMethod.GET)
    public ModelAndView addChallengePage() {
        ModelAndView modelAndView = prepareChallengeManagementViewModel(ManagementMode.Add);
        return modelAndView;
    }

    @RequestMapping(value = "/saveChallenge", method = RequestMethod.POST)
    @ResponseBody
    public SaveChallengeResponse saveChallenge(@RequestBody ChallengeModel challengeModel, BindingResult bindingResult) {
        saveChallengeValidator.validate(challengeModel, bindingResult);

        if (bindingResult.hasErrors()) {
            return SaveChallengeResponse.failedResponse(ValidationUtils.extractAllErrorCodes(bindingResult));
        }

        Long challengeId = challengeService.saveChallenge(challengeModel.createChallenge());

        return SaveChallengeResponse.successResponse(challengeId);
    }

    @RequestMapping(value = "/updateChallenge/{challengeId}", method = RequestMethod.GET)
    public ModelAndView updateChallengePage(@PathVariable("challengeId") long challengeId) {
        ModelAndView modelAndView = prepareChallengeManagementViewModel(ManagementMode.Update);

        Challenge challenge = challengeService.findById(challengeId);
        modelAndView.addObject("challengeModel", JsonUtils.toJsonString(ChallengeModel.createFrom(challenge)));

        return modelAndView;
    }

    private ModelAndView prepareChallengeManagementViewModel(ManagementMode managementMode) {
        ModelAndView modelAndView = new ModelAndView("challenge/manageChallenge");
        modelAndView.addObject("mode", managementMode);
        modelAndView.addObject("lingos", Lingo.values());
        modelAndView.addObject("dataTypes", DataType.values());
        return modelAndView;
    }

}