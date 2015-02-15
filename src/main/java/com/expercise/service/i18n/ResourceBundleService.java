package com.expercise.service.i18n;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Service
public class ResourceBundleService {

    public String getMessage(String bundleName, String key) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName, LocaleContextHolder.getLocale(), classLoader);
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return "???_" + key + "_???";
        }
    }

}
