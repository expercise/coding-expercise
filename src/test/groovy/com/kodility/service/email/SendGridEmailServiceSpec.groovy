package com.kodility.service.email

import com.kodility.service.email.model.Email
import com.sendgrid.SendGrid
import spock.lang.Specification

class SendGridEmailServiceSpec extends Specification {

    EmailService service

    SendGrid sendGridClient = Mock()

    SendGrid.Email emailArgument

    def setup() {
        service = new SendGridEmailService(sendGridClient: sendGridClient)
    }

    def "should send email via SendGrid with proper parameters"() {
        given:
        Email emailToSend = new Email(to: "user@kodility.com", from: "testmail@kodility.com", subject: "Subject of the email", text: "Content of the email")

        when:
        service.send(emailToSend)

        then:
        1 * sendGridClient.send({ emailArgument = it }) >> new SendGrid.Response(200, "email sent successfully")
        emailArgument.getTos()[0] == "user@kodility.com"
        emailArgument.getFrom() == "testmail@kodility.com"
        emailArgument.getSubject() == "Subject of the email"
        emailArgument.getText() == "Content of the email"
    }

}
