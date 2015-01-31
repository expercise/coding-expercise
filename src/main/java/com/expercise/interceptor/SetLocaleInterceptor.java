package com.expercise.interceptor;

import com.expercise.enums.Lingo;
import com.expercise.service.user.AuthenticationService;
import com.expercise.utils.CookieUtils;
import com.expercise.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SetLocaleInterceptor extends LocaleChangeInterceptor {

    public static final String LOCALE_COOKIE_NAME = "locale";

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CookieUtils cookieUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        Optional<String> localeFromCookie = cookieUtils.getCookieValue(LOCALE_COOKIE_NAME);
        Optional<String> localeFromRequest = Optional.ofNullable(request.getParameter(getParamName()));

        if (localeFromRequest.isPresent()) {
            setLocaleAndCookie(request, response, localeFromRequest.get());
        } else if (localeFromCookie.isPresent()) {
            setLocaleAndCookie(request, response, localeFromCookie.get());
        } else if (authenticationService.isCurrentUserAuthenticated()) {
            Lingo lingo = authenticationService.getCurrentUser().getLingo();
            if (lingo != null) {
                setLocaleAndCookie(request, response, lingo.getShortName());
            }
        }

        return true;
    }

    private void setLocaleAndCookie(HttpServletRequest request, HttpServletResponse response, String locale) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        localeResolver.setLocale(request, response, StringUtils.parseLocaleString(locale));
        cookieUtils.setCookieValue(response, LOCALE_COOKIE_NAME, locale, DateUtils.ONE_MONTH);
    }

}
