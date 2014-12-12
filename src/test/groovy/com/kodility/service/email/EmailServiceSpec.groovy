package com.kodility.service.email

import com.kodility.service.email.model.Email
import com.kodility.service.i18n.MessageService
import spock.lang.Specification

class EmailServiceSpec extends Specification {

    private EmailService service

    private MessageService messageService = Mock();
    private EmailTemplateProcessor emailTemplateProcessor = Mock();
    private EmailSenderService emailSenderService = Mock();

    private Email emailCaptor

    def setup() {
        def dependencies = [messageService        : messageService,
                            emailTemplateProcessor: emailTemplateProcessor,
                            emailSenderService    : emailSenderService]
        service = new EmailService(dependencies)
    }

    def "should prepare and send email"() {
        setup:
        service.emailStatus = "active"
        def emailToSend = new Email(to:"user@kodiliy.com", from:"hq@kodiliy.com", subjectKey: "emailSubjectKey", contentKey: "emailContentKey")
        when:
        service.send(emailToSend, new HashMap<String, Object>())
        then:
        1 * messageService.getMessage("emailSubjectKey") >> "email subject"
        1 * emailTemplateProcessor.createEmail("emailContentKey", [:]) >> "email content"
        1 * emailSenderService.send({emailCaptor = it})
        emailCaptor.to == "user@kodiliy.com"
        emailCaptor.from == "hq@kodiliy.com"
        emailCaptor.subject == "email subject"
        emailCaptor.content == "email content"
    }

    def "should not prepare and send prepare and send email from development environment"() {
        setup:
        service.emailStatus = "deactive"
        def emailToSend = new Email(to:"user@kodiliy.com", from:"hq@kodiliy.com", subjectKey: "emailSubjectKey", contentKey: "emailContentKey")
        when:
        service.send(emailToSend, new HashMap<String, Object>())
        then:
        0 * _
    }

}
