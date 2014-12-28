package com.kodility.service.user

import com.kodility.domain.token.TokenType
import com.kodility.domain.user.User
import com.kodility.service.email.EmailService
import com.kodility.service.email.model.Email
import com.kodility.service.util.TokenService
import com.kodility.service.util.UrlService
import spock.lang.Specification

class ForgotMyPasswordServiceSpec extends Specification {

    private EmailService emailService = Mock()

    private UrlService urlService = Mock()

    private TokenService tokenService = Mock()

    private ForgotMyPasswordService service

    Email emailCaptor
    Map<String, Object> paramCaptor

    def setup() {
        def dependencies = [emailService: emailService,
                            urlService  : urlService,
                            tokenService: tokenService]
        service = new ForgotMyPasswordService(dependencies)
    }

    def "should send reset password email to user"() {
        given:
        User user = new User(id: 1L, email: "user@kodility.com", firstName: "Ahmet", lastName: "Mehmet")
        1 * tokenService.createTokenFor(user, TokenType.FORGOT_MY_PASSWORD) >> "token_123"
        1 * urlService.createUrlFor("/forgotMyPassword/reset?token=token_123") >> "http://www.kodility.com/forgotMyPassword/reset?token=token_123"
        when:
        service.sendResetEmail(user)
        then: "verify email content and parameters"
        1 * emailService.send({ emailCaptor = it } as Email, { paramCaptor = it } as Map)
        emailCaptor.to == "user@kodility.com"
        emailCaptor.from == "passwordservice@kodility.com"
        emailCaptor.subjectKey == "forgotmypassword.subject"
        emailCaptor.templateName == "forgotmypassword_email"
        and: "verify email parameters"
        paramCaptor.user == user
        paramCaptor.url == "http://www.kodility.com/forgotMyPassword/reset?token=token_123"
    }

}
