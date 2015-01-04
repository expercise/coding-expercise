package com.kodility.controller

import spock.lang.Specification
import spock.lang.Unroll

class RedirectUtilSpec extends Specification {

    @Unroll
    def "should redirect #path to #redirectedPath"() {
        expect:
        RedirectUtil.redirectTo(path).getViewName() == redirectedPath

        where:
        path               | redirectedPath
        "/"                | "redirect:/"
        "/login"           | "redirect:/login"
        "/login?newmember" | "redirect:/login?newmember"
        "/404"             | "redirect:/404"
    }

}
