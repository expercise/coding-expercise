package com.kodility.controller.user;

import com.kodility.controller.RedirectUtil;
import com.kodility.controller.user.model.UserModel;
import com.kodility.domain.user.User;
import com.kodility.enums.Lingo;
import com.kodility.enums.ProgrammingLanguage;
import com.kodility.service.user.UserService;
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
