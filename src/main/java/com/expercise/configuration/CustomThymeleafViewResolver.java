package com.expercise.configuration;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.Locale;

public class CustomThymeleafViewResolver extends ThymeleafViewResolver {

    @Override
    protected View createView(String viewName, Locale locale) throws Exception {
        View view = super.createView(viewName, locale);
        if (view.getClass().isAssignableFrom(RedirectView.class)) {
            ((RedirectView) view).setExposeModelAttributes(false);
        }
        return view;
    }

}
