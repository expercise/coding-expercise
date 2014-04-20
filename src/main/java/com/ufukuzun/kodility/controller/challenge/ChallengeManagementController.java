package com.ufukuzun.kodility.controller.challenge;

import com.ufukuzun.kodility.controller.challenge.model.ManagementMode;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.DataTypes;
import com.ufukuzun.kodility.enums.Lingo;
import com.ufukuzun.kodility.service.challenge.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/challenges")
public class ChallengeManagementController {

    @Autowired
    private ChallengeService challengeService;

    @RequestMapping(value = "/addChallenge", method = RequestMethod.GET)
    public ModelAndView addChallengePage() {
        ModelAndView modelAndView = new ModelAndView("challenge/manageChallenge");

        addManagementMode(modelAndView, ManagementMode.Add);
        modelAndView.addObject("lingos", Lingo.values());
        modelAndView.addObject("dataTypes", DataTypes.values());

        return modelAndView;
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