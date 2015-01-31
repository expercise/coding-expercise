package com.expercise.controller;

import org.springframework.web.servlet.ModelAndView;

public final class RedirectUtil {

    private RedirectUtil() {
    }

    public static ModelAndView redirectHome() {
        return redirectTo("/");
    }

    public static ModelAndView redirectLoginForNewMember() {
        return redirectLoginFor("newMember");
    }

    public static ModelAndView redirectLoginFor(String purpose) {
        return redirectTo("/login?" + purpose);
    }

    // TODO ufuk: create a 404 page
    public static ModelAndView redirect404() {
        return redirectTo("/404");
    }

    public static ModelAndView redirectTo(String url) {
        return new ModelAndView("redirect:" + url);
    }

}
