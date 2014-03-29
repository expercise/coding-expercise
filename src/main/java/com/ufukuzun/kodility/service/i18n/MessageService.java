package com.ufukuzun.kodility.service.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private ResourceBundleMessageSource bundleMessageSource;

    public String getMessage(String code) {
        return bundleMessageSource.getMessage(code, new Object[]{}, LocaleContextHolder.getLocale());
    }

}
