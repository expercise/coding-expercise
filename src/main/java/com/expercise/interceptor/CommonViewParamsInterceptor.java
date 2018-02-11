package com.expercise.interceptor;

import com.expercise.service.configuration.Configurations;
import com.expercise.service.user.AuthenticationService;
import com.expercise.service.util.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonViewParamsInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private Configurations configurations;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UrlService urlService;

    @Value("${build.id}")
    private String buildId;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (!isHandlingAvailable(modelAndView)) {
            return;
        }

        modelAndView.addObject("buildId", buildId);
        modelAndView.addObject("developmentEnvironment", configurations.isDevelopment());
        modelAndView.addObject("googleAnalyticsApplicationKey", configurations.getGoogleAnalyticsApplicationKey());
        modelAndView.addObject("currentUser", authenticationService.getCurrentUser());
        modelAndView.addObject("mobileClient", DeviceUtils.getCurrentDevice(request).isMobile() || DeviceUtils.getCurrentDevice(request).isTablet());
        modelAndView.addObject("canonical", urlService.getCanonical(request));
    }

    private boolean isHandlingAvailable(ModelAndView modelAndView) {
        return modelAndView != null && modelAndView.hasView() && !(modelAndView.getView() instanceof RedirectView);
    }

}
