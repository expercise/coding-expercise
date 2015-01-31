package com.expercise.service.email

import com.expercise.service.email.model.Email
import com.sendgrid.SendGrid
import spock.lang.Specification

class SendGridEmailServiceSpec extends Specification {

    EmailSenderService service

    SendGrid sendGridClient = Mock()

    SendGrid.Email emailArgument

    def setup() {
        service = new SendGridEmailSenderService(sendGridClient: sendGridClient)
    }

    def "should send email via SendGrid with proper parameters"() {
        given:
        Email emailToSend = new Email(to: "user@expercise.com", from: "noreply@expercise.com", subject: "Subject of the email", content: "Content of the email")

        when:
        service.send(emailToSend)

        then:
        1 * sendGridClient.send({ emailArgument = it }) >> new SendGrid.Response(200, "email sent successfully")
        emailArgument.getTos()[0] == "user@expercise.com"
        emailArgument.getFrom() == "noreply@expercise.com"
        emailArgument.getSubject() == "Subject of the email"
        emailArgument.getHtml() == "Content of the email"
    }

}
