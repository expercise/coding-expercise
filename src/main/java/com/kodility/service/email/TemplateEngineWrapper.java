package com.kodility.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class TemplateEngineWrapper {

    @Autowired
    private TemplateEngine templateEngine;

    public String process(String templateName, Context context){
        return templateEngine.process(templateName, context);
    }

}
