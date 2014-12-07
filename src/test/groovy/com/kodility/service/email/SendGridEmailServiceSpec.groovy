package com.kodility.service.email

import com.kodility.service.email.model.Email
import com.sendgrid.SendGrid
import spock.lang.Specification

class SendGridEmailServiceSpec extends Specification {

    private sendGridClient = Mock(SendGrid)
    private EmailService service

    def setup() {
        service = new SendGridEmailService(sendGridClient: sendGridClient)
    }

    def "should send sendGrid email with proper parameters"() {
        given:
        Email emailToSend = new Email(
                to: "user@kodility.com",
                from: "testmail@kodility.com",
                subject: "subject of the email",
                text: "Content of the email")
        when:
        service.send(emailToSend)
        then:
        1 * sendGridClient.send({ emailArg ->
                    emailArg.getTos()[0] == "user@kodility.com" &&
                    emailArg.getFrom() == "testmail@kodility.com" &&
                    emailArg.getSubject() == "subject of the email" &&
                    emailArg.getText() == "Content of the email"
                }) >> new SendGrid.Response(200, "email sent successfully")
    }

}
