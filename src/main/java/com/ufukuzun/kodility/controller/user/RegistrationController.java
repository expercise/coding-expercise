package com.ufukuzun.kodility.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    @RequestMapping("/register")
    public ModelAndView registrationPage() {
        return new ModelAndView("register");
    }

}
