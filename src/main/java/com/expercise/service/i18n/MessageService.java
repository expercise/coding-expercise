package com.expercise.service.i18n;

import com.expercise.configuration.MessagesResourceBundleSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageService {

    @Autowired
    private MessagesResourceBundleSource bundleMessageSource;

    public String getMessage(String code) {
        return getMessage(code, new Object[]{});
    }

    public String getMessage(String code, Object... args) {
        return getMessage(code, LocaleContextHolder.getLocale(), args);
    }

    public String getMessage(String code, Locale locale) {
        return getMessage(code, locale, new Object[]{});
    }

    public String getMessage(String code, Locale locale, Object... args) {
        return bundleMessageSource.getMessage(code, args, locale);
    }

    public Map<String, String> getAllMessages() {
        Map<String, String> messages = new ConcurrentHashMap<>();

        ResourceBundle messagesResourceBundle = bundleMessageSource.getMessagesResourceBundle();
        messagesResourceBundle.keySet().forEach(
                k -> messages.put(k, messagesResourceBundle.getString(k))
        );

        return messages;
    }

}
