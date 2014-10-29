package com.ufukuzun.kodility.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtils {

    @Autowired
    private HttpServletRequest request;

    public String getCookieValue(String name) {
        return Optional.ofNullable(request.getCookies())
                .map(cookies -> Arrays.asList(cookies))
                .orElse(new ArrayList<>()).stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .map(c -> c.getValue())
                .orElse(null);
    }

    public void setCookieValue(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
