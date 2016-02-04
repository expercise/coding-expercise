package com.expercise.controller;

import org.springframework.web.servlet.ModelAndView;

public final class RedirectUtils {

    private RedirectUtils() {
    }

    public static ModelAndView redirectHome() {
        return redirectTo("/");
    }

    public static ModelAndView redirectThemesForNewMember() {
        return redirectTo("/themes?" + "newMember");
    }

    public static ModelAndView redirectLoginFor(String purpose) {
        return redirectTo("/login?" + purpose);
    }

    public static ModelAndView redirectLogin() {
        return redirectTo("/login");
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
