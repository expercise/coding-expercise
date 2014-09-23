package com.ufukuzun.kodility.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class CookieUtils {

    @Autowired
    private HttpServletRequest request;

    public String getCookieValue(final String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        Cookie cookie = (Cookie) CollectionUtils.find(Arrays.asList(cookies), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((Cookie) object).getName().equals(name);
            }
        });

        return cookie != null ? cookie.getValue() : null;
    }

    public void setCookieValue(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
