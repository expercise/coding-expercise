package com.ufukuzun.kodility.controller.user;

import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute @Valid User user, BindingResult bindingResult, ModelAndView modelAndView) {
        validateWhetherEmailAddressIsAlreadyRegistered(user, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
            return modelAndView;
        } else {
            userService.saveNewUser(user);
            return new ModelAndView("redirect:/login?newMember");
        }
    }

    private void validateWhetherEmailAddressIsAlreadyRegistered(User user, BindingResult bindingResult) {
        if (userService.isEmailAlreadyRegistered(user.getEmail())) {
            bindingResult.addError(new FieldError("user", "email", user.getEmail(), false, new String[]{"NotUnique.user.email"}, null, null));
        }
    }

}
