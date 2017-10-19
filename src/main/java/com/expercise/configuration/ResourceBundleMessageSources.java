package com.expercise.configuration;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.ResourceBundle;

public class ResourceBundleMessageSources extends ResourceBundleMessageSource {

    public ResourceBundle getApplicationMessages() {
        return getResourceBundle("messages", LocaleContextHolder.getLocale());
    }

    public ResourceBundle getEmailMessages() {
        return getResourceBundle("messagesForEmails", LocaleContextHolder.getLocale());
    }

}
