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

    private static final String MESSAGES_FOR_EMAILS_BUNDLE_NAME = "messagesForEmails";

    @Autowired
    private MessagesResourceBundleSource messagesBundleSource;

    @Autowired
    private AlternateMessageResourceBundleService alternateMessageResourceBundleService;

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
        ResourceBundle messagesResourceBundle = messagesBundleSource.getMessagesResourceBundle();
        messagesResourceBundle.keySet().forEach(
                k -> messages.put(k, messagesResourceBundle.getString(k))
        );
        return messages;
    }

    public String getMessageForEmail(String key) {
        return alternateMessageResourceBundleService.getMessage(MESSAGES_FOR_EMAILS_BUNDLE_NAME, key);
    }

}
