package com.ufukuzun.kodility.controller.user;

import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.service.challenge.LevelService;
import com.ufukuzun.kodility.service.challenge.SolutionService;
import com.ufukuzun.kodility.service.challenge.UserPointService;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import com.ufukuzun.kodility.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private SolutionService solutionService;

    @RequestMapping
    public ModelAndView showMyProfile() {
        User user = authenticationService.getCurrentUser();
        ModelAndView modelAndView = prepareModelAndView(user, true);
        modelAndView.addObject("solvedChallenges", solutionService.getSolvedChallengesOf(user));
        return modelAndView;
    }

    @RequestMapping("/{userId}")
    public ModelAndView showPublicProfile(@PathVariable long userId) {
        User user = userService.findById(userId);
        return prepareModelAndView(user, false);
    }

    private ModelAndView prepareModelAndView(User user, boolean currentUser) {
        ModelAndView modelAndView = new ModelAndView("user/profile");
        modelAndView.addObject("user", user);
        modelAndView.addObject("experiencePoint", userPointService.getTotalPointsOf(user));
        modelAndView.addObject("isCurrentUser", currentUser);
        modelAndView.addObject("currentLevelModel", levelService.getCurrentLevelModelOfCurrentUser());
        return modelAndView;
    }

}
