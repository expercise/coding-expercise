package com.expercise.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// TODO ufuk: re-implement with @ControllerAdvice and @ExceptionHandler mechanisms
@Controller
public class ErrorController {

    @RequestMapping("/403")
    public ModelAndView get403() {
        return prepareModelAndViewFor(403);
    }

    @RequestMapping("/404")
    public ModelAndView get404() {
        return prepareModelAndViewFor(404);
    }

    @RequestMapping("/500")
    public ModelAndView get500() {
        return prepareModelAndViewFor(500);
    }

    private ModelAndView prepareModelAndViewFor(int errorCode) {
        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("errorCode", errorCode);
        return modelAndView;
    }

}
