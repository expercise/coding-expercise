package com.ufukuzun.kodility.controller.challenge;

import com.ufukuzun.kodility.controller.challenge.model.SaveChallengeRequest;
import com.ufukuzun.kodility.controller.challenge.model.ManagementMode;
import com.ufukuzun.kodility.controller.challenge.model.SaveChallengeResponse;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.enums.Lingo;
import com.ufukuzun.kodility.service.challenge.ChallengeService;
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
        ModelAndView modelAndView = new ModelAndView("challenge/manageChallenge");

        addManagementMode(modelAndView, ManagementMode.Add);
        modelAndView.addObject("lingos", Lingo.values());
        modelAndView.addObject("dataTypes", DataType.values());

        return modelAndView;
    }

    @RequestMapping(value = "/saveChallenge", method = RequestMethod.POST)
    @ResponseBody
    public SaveChallengeResponse saveChallenge(@RequestBody SaveChallengeRequest saveChallengeRequest, BindingResult bindingResult) {
        saveChallengeValidator.validate(saveChallengeRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            return SaveChallengeResponse.failedResponse(ValidationUtils.extractAllErrorCodes(bindingResult));
        }

        String challengeId = challengeService.saveChallenge(saveChallengeRequest.createChallenge());

        return SaveChallengeResponse.successResponse(challengeId);
    }

    @RequestMapping(value = "/updateChallenge/{challengeId}", method = RequestMethod.GET)
    public ModelAndView updateChallengePage(@PathVariable String challengeId) {
        ModelAndView modelAndView = new ModelAndView("challenge/manageChallenge");

        Challenge challenge = challengeService.findById(challengeId);
        modelAndView.addObject("challenge", challenge);

        // TODO ufuk: complete

        addManagementMode(modelAndView, ManagementMode.Update);

        return modelAndView;
    }

    private void addManagementMode(ModelAndView modelAndView, ManagementMode managementMode) {
        modelAndView.addObject("mode", managementMode);
    }

}