package com.ufukuzun.kodility.interceptor;

import com.ufukuzun.kodility.enums.Lingo;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import com.ufukuzun.kodility.utils.CookieUtils;
import com.ufukuzun.kodility.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetLocaleInterceptor extends LocaleChangeInterceptor {

    public static final String LOCALE_COOKIE_NAME = "locale";

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CookieUtils cookieUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        String localeFromCookie = cookieUtils.getCookieValue(LOCALE_COOKIE_NAME);
        String localeFromRequest = request.getParameter(getParamName());

        if (localeFromRequest != null) {
            setLocaleAndCookie(request, response, localeFromRequest);
        } else if (localeFromCookie != null) {
            setLocaleAndCookie(request, response, localeFromCookie);
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
