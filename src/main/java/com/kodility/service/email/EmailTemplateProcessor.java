package com.kodility.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailTemplateProcessor {

    @Autowired
    private TemplateEngineWrapper templateEngineWrapper;

    public String createEmail(String emailFileName, Map<String, Object> params) {
        Context context = new Context();
        params.entrySet().stream().forEach(it -> context.setVariable(it.getKey(), it.getValue()));
        return templateEngineWrapper.process(emailFileName, context);
    }

}
