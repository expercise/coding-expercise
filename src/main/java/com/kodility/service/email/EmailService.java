package com.kodility.service.email;

import com.kodility.service.email.model.Email;
import com.kodility.service.i18n.MessageService;
import com.kodility.utils.EnvironmentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private EmailTemplateProcessor emailTemplateProcessor;

    @Autowired
    private EmailSenderService emailSenderService;

    @Value("${environment}")
    private String environment;

    public void send(Email email, Map<String, Object> params) {
        if (EnvironmentUtils.isDevelopment(environment)) {
            LOGGER.info("could not send email because environment is development");
            return;
        }
        email.setSubject(messageService.getMessage(email.getSubjectKey()));
        email.setContent(emailTemplateProcessor.createEmail(email.getContentKey(), params));
        emailSenderService.send(email);
    }

}
