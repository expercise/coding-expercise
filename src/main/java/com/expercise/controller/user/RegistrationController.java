package com.expercise.controller.user;

import com.expercise.controller.RedirectUtils;
import com.expercise.controller.user.model.UserModel;
import com.expercise.domain.user.User;
import com.expercise.enums.Lingo;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.service.user.AuthenticationService;
import com.expercise.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @RequestMapping("/register")
    public ModelAndView registrationPage(ModelAndView modelAndView) {
        initializeModelAndView(modelAndView);
        modelAndView.addObject("userModel", new UserModel());
        return modelAndView;
    }

    @RequestMapping("/socialRegister")
    public ModelAndView socialRegister(HttpServletRequest request) {
        try {
            ProviderSignInAttempt providerSignInAttempt = (ProviderSignInAttempt) request.getSession().getAttribute(ProviderSignInAttempt.SESSION_ATTRIBUTE);
            Connection<?> connection = providerSignInAttempt.getConnection(connectionFactoryLocator);

            UserProfile userProfile = connection.fetchUserProfile();
            ConnectionData connectionData = connection.createData();

            User user = userService.saveSocialUser(userProfile, connectionData);

            if (authenticationService.isCurrentUserAuthenticated()) {
                return RedirectUtils.redirectProfile();
            } else {
                authenticationService.authenticate(connection, user);
                return RedirectUtils.redirectThemesForNewMember();
            }
        } catch (Exception e) {
            LOGGER.error("Exception while social sign up: ", e);
            return RedirectUtils.redirectLogin();
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute @Valid UserModel userModel, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            initializeModelAndView(modelAndView);
            return modelAndView;
        }

        User user = userModel.createUser();
        userService.saveNewUser(user);

        authenticationService.authenticate(userModel.getEmail(), userModel.getPassword());

        return RedirectUtils.redirectThemesForNewMember();
    }

    private void initializeModelAndView(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        modelAndView.addObject("programmingLanguages", ProgrammingLanguage.values());
        modelAndView.addObject("lingos", Lingo.values());
    }

}
