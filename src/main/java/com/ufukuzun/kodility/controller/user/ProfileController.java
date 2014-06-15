package com.ufukuzun.kodility.controller.user;

import com.ufukuzun.kodility.domain.user.User;
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

    @RequestMapping
    public ModelAndView showMyProfile() {
        User user = authenticationService.getCurrentUser();
        return prepareModelAndView(user, true);
    }

    @RequestMapping("/{userId}")
    public ModelAndView showPublicProfile(@PathVariable long userId) {
        User user = userService.findById(userId);
        return prepareModelAndView(user, false);
    }

    // TODO ufuk: put user's level progress info
    private ModelAndView prepareModelAndView(User user, boolean currentUser) {
        ModelAndView modelAndView = new ModelAndView("user/profile");
        modelAndView.addObject("user", user);
        modelAndView.addObject("experiencePoint", userPointService.getTotalPointsOf(user));
        modelAndView.addObject("isCurrentUser", currentUser);
        return modelAndView;
    }

}
