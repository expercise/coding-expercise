package com.expercise.controller.challenge;

import com.expercise.controller.challenge.model.TagModel;
import com.expercise.domain.challenge.Challenge;
import com.expercise.service.challenge.ChallengeService;
import com.expercise.service.challenge.TagService;
import com.expercise.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class StartCodingController {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @RequestMapping("/start-coding")
    public ModelAndView listTagsWithChallengeCounts() {
        if (userService.isNewbie()) {
            Optional<Challenge> defaultChallenge = challengeService.getDefaultChallenge();
            if (defaultChallenge.isPresent()) {
                return new ModelAndView("redirect:/challenges/" + defaultChallenge.get().getId() + "?newMember");
            }
        }

        ModelAndView modelAndView = new ModelAndView("tag/tagsList");
        List<TagModel> tagModels = tagService.prepareTagModels();
        modelAndView.addObject("tagList", tagModels);

        return modelAndView;
    }

}
