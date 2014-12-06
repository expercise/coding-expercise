package com.kodility.controller;

import com.kodility.service.quote.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private QuoteService quoteService;

    @RequestMapping
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("quote", quoteService.randomQuote());
        return modelAndView;
    }

}
