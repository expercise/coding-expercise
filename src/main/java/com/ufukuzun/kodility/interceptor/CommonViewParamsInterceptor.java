package com.ufukuzun.kodility.interceptor;

import com.ufukuzun.kodility.service.configuration.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonViewParamsInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ConfigurationService configurationService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("developmentEnvironment", configurationService.isDevelopment());
        modelAndView.addObject("googleAnalyticsScript", configurationService.getGoogleAnalyticsScript());
    }

}
