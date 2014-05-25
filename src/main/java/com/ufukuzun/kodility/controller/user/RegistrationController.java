package com.ufukuzun.kodility.controller.user;

import com.ufukuzun.kodility.controller.RedirectUtil;
import com.ufukuzun.kodility.controller.user.model.UserModel;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.Lingo;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
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
        modelAndView.addObject("userModel", new UserModel());
        modelAndView.addObject("programmingLanguages", ProgrammingLanguage.values());
        modelAndView.addObject("lingos", Lingo.values());
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute @Valid UserModel userModel, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
            modelAndView.addObject("programmingLanguages", ProgrammingLanguage.values());
            modelAndView.addObject("lingos", Lingo.values());
            return modelAndView;
        }

        User user = userModel.createUser();
        userService.saveUser(user);

        return RedirectUtil.redirectLoginForNewMember();
    }

}
