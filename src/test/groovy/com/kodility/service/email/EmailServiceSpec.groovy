package com.kodility.service.email
import com.kodility.service.email.model.Email
import com.kodility.service.i18n.MessageService
import spock.lang.Specification

class EmailServiceSpec extends Specification {

    EmailService service

    MessageService messageService = Mock();

    EmailTemplateProcessor emailTemplateProcessor = Mock();

    EmailSenderService emailSenderService = Mock();

    Email emailCaptor

    def setup() {
        def dependencies = [messageService        : messageService,
                            emailTemplateProcessor: emailTemplateProcessor,
                            emailSenderService    : emailSenderService]
        service = new EmailService(dependencies)
    }

    def "should prepare and send email if email sending is activated"() {
        given:
        service.emailStatus = "active"
        def emailToSend = new Email(to: "user@kodiliy.com", from: "noreply@kodiliy.com", subjectKey: "emailSubjectKey", templateName: "emailTemplateName")

        when:
        service.send(emailToSend, new HashMap<String, Object>())

        then:
        1 * messageService.getMessage("emailSubjectKey") >> "email subject"
        1 * emailTemplateProcessor.createEmail("emailTemplateName", [:]) >> "email content"
        1 * emailSenderService.send({ emailCaptor = it })
        emailCaptor.to == "user@kodiliy.com"
        emailCaptor.from == "noreply@kodiliy.com"
        emailCaptor.subject == "email subject"
        emailCaptor.content == "email content"
    }

    def "should not prepare and send email if email sending is deactivated"() {
        given:
        service.emailStatus = "deactive"
        def emailToSend = new Email(to: "user@kodiliy.com", from: "noreply@kodiliy.com", subjectKey: "emailSubjectKey", templateName: "emailTemplateName")

        when:
        service.send(emailToSend, new HashMap<String, Object>())

        then:
        0 * _
    }

}
