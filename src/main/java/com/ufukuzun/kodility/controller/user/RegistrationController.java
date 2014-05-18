package com.ufukuzun.kodility.controller.user;

import com.ufukuzun.kodility.controller.user.model.UserModel;
import com.ufukuzun.kodility.service.user.UserService;
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
    public ModelAndView registrationPage() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new UserModel());
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("user") @Valid UserModel userModel, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
            return modelAndView;
        } else {
            userService.saveNewUser(userModel.createUser());
            return new ModelAndView("redirect:/login?newMember");
        }
    }

}
