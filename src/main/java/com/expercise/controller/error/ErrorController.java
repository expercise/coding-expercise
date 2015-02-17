package com.expercise.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/404")
    public String get404() {
        return "error/404";
    }

}
