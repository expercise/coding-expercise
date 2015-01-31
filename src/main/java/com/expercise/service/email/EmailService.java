package com.expercise.service.email;

import com.expercise.service.email.model.Email;
import com.expercise.service.i18n.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private EmailTemplateProcessor emailTemplateProcessor;

    @Autowired
    private EmailSenderService emailSenderService;

    @Value("${email.status}")
    private String emailStatus;

    public void send(Email email, Map<String, Object> params) {
        if ("deactive".equals(emailStatus)) {
            return;
        }
        // TODO ufuk & batu: we must use different message bundle for emails
        email.setSubject(messageService.getMessage(email.getSubjectKey()));
        email.setContent(emailTemplateProcessor.createEmail(email.getTemplateName(), params));
        emailSenderService.send(email);
    }

}
