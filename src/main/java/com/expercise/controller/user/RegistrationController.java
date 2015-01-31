package com.expercise.controller.user;

import com.expercise.controller.RedirectUtil;
import com.expercise.controller.user.model.UserModel;
import com.expercise.domain.user.User;
import com.expercise.enums.Lingo;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public ModelAndView registrationPage(ModelAndView modelAndView) {
        initializeModelAndView(modelAndView);
        modelAndView.addObject("userModel", new UserModel());
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute @Valid UserModel userModel, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            initializeModelAndView(modelAndView);
            return modelAndView;
        }

        User user = userModel.createUser();
        userService.saveUser(user);

        return RedirectUtil.redirectLoginForNewMember();
    }

    private void initializeModelAndView(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        modelAndView.addObject("programmingLanguages", ProgrammingLanguage.values());
        modelAndView.addObject("lingos", Lingo.values());
    }

}
