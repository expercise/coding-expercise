package com.ufukuzun.kodility.controller;

import org.springframework.web.servlet.ModelAndView;

public final class RedirectUtil {

    private RedirectUtil() {
    }

    public static ModelAndView redirectHome() {
        return redirect("/");
    }

    public static ModelAndView redirectLoginForNewMember() {
        return redirect("/login?newMember");
    }

    public static ModelAndView redirect404() {
        return redirect("/404");    // TODO ufuk: create a 404 page
    }

    private static ModelAndView redirect(String url) {
        return new ModelAndView("redirect:" + url);
    }

}
