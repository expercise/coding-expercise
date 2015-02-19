package com.expercise.service.email;

import com.expercise.service.i18n.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailTemplateProcessor {

    @Autowired
    private TemplateEngineWrapper templateEngineWrapper;

    @Autowired
    private MessageService messageService;

    public String createEmail(String emailTemplateName, Map<String, Object> params) {
        Context context = new Context();
        params.entrySet().stream().forEach(it -> context.setVariable(it.getKey(), it.getValue()));
        context.setVariable("msg", messageService);
        return templateEngineWrapper.process(emailTemplateName, context);
    }

}
