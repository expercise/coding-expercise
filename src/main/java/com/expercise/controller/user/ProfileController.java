package com.expercise.controller.user;

import com.expercise.controller.RedirectUtils;
import com.expercise.domain.user.User;
import com.expercise.service.challenge.ChallengeService;
import com.expercise.service.challenge.SolutionService;
import com.expercise.service.challenge.UserPointService;
import com.expercise.service.user.AuthenticationService;
import com.expercise.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private SolutionService solutionService;

    @Autowired
    private ChallengeService challengeService;

    @RequestMapping
    public ModelAndView showMyProfile() {
        User user = authenticationService.getCurrentUser();
        return prepareModelAndView(user);
    }

    @RequestMapping("/{userId}/{bookmarkableFullName}")
    public ModelAndView showPublicProfile(@PathVariable long userId) {
        User user = userService.findById(userId);
        return prepareModelAndView(user);
    }

    @RequestMapping("/selectAvatar/{avatar}")
    public ModelAndView showPublicProfile(@PathVariable String avatar) {
        User user = authenticationService.getCurrentUser();
        user.setAvatar(avatar);
        userService.updateUser(user);
        return RedirectUtils.redirectTo(user.getBookmarkableUrl());
    }

    private ModelAndView prepareModelAndView(User user) {
        ModelAndView modelAndView = new ModelAndView("user/profile");
        modelAndView.addObject("user", user);
        modelAndView.addObject("experiencePoint", userPointService.getTotalPointsOf(user));
        modelAndView.addObject("addedChallenges", challengeService.findAllApprovedChallengesOfUser(user));
        modelAndView.addObject("solvedChallenges", solutionService.getSolvedChallengesOf(user));
        // TODO ufuk: fix, show current level in different way
        // modelAndView.addObject("currentLevelModel", levelService.getCurrentLevelModelOf(user, null));
        return modelAndView;
    }

}
