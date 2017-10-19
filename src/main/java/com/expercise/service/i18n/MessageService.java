package com.expercise.service.i18n;

import com.expercise.configuration.ResourceBundleMessageSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageService {

    @Autowired
    private ResourceBundleMessageSources messagesBundleSource;

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
        return messagesBundleSource.getMessage(code, args, locale);
    }

    public Map<String, String> getAllMessages() {
        Map<String, String> messages = new ConcurrentHashMap<>();
        ResourceBundle messagesResourceBundle = messagesBundleSource.getApplicationMessages();
        messagesResourceBundle.keySet().forEach(
                k -> messages.put(k, messagesResourceBundle.getString(k))
        );
        return messages;
    }

    public String getMessageForEmail(String key, Object... args) {
        ResourceBundle resourceBundle = messagesBundleSource.getEmailMessages();
        try {
            String foundRawMessage = resourceBundle.getString(key);
            MessageFormat messageFormat = new MessageFormat(foundRawMessage, LocaleContextHolder.getLocale());
            return messageFormat.format(args);
        } catch (MissingResourceException e) {
            return "???_" + key + "_???";
        }
    }

}
