package com.kodility.service.i18n;

import com.kodility.configuration.MessagesResourceBundleSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageService {

    @Autowired
    private MessagesResourceBundleSource bundleMessageSource;

    public String getMessage(String code) {
        return bundleMessageSource.getMessage(code, new Object[]{}, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code, Object... args) {
        return bundleMessageSource.getMessage(code, args, LocaleContextHolder.getLocale());
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
