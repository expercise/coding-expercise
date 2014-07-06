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

    // TODO ufuk: create a 404 page
    public static ModelAndView redirect404() {
        return redirect("/404");
    }

    private static ModelAndView redirect(String url) {
        return new ModelAndView("redirect:" + url);
    }

}
