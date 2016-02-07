package com.expercise.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping("/signin")
    public ModelAndView loginPage() {
        return new ModelAndView("signin");
    }

}
