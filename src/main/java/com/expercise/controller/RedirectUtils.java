package com.expercise.controller;

import org.springframework.web.servlet.ModelAndView;

public final class RedirectUtils {

    private RedirectUtils() {
    }

    public static ModelAndView redirectHome() {
        return redirectTo("/");
    }

    public static ModelAndView redirectTagsForNewMember() {
        return redirectTo("/start-coding?" + "newMember");
    }

    public static ModelAndView redirectLoginFor(String purpose) {
        return redirectTo("/signin?" + purpose);
    }

    public static ModelAndView redirectLogin() {
        return redirectTo("/signin");
    }

    public static ModelAndView redirectProfile() {
        return redirectTo("/user");
    }

    public static ModelAndView redirect403() {
        return redirectTo("/403");
    }

    public static ModelAndView redirect404() {
        return redirectTo("/404");
    }

    public static ModelAndView redirectTo(String url) {
        return new ModelAndView("redirect:" + url);
    }

}
