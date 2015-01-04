package com.kodility.service.user

import com.kodility.domain.token.Token
import com.kodility.domain.token.TokenType
import com.kodility.domain.user.User
import com.kodility.service.email.EmailService
import com.kodility.service.email.model.Email
import com.kodility.service.util.TokenService
import com.kodility.service.util.UrlService
import spock.lang.Specification

class ForgotMyPasswordServiceSpec extends Specification {

    ForgotMyPasswordService service

    EmailService emailService = Mock()
    UrlService urlService = Mock()
    TokenService tokenService = Mock()

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
        emailCaptor.subjectKey == "forgotMyPassword.subject"
        emailCaptor.templateName == "forgotMyPasswordEmail"

        and: "verify email parameters"
        paramCaptor.user == user
        paramCaptor.url == "http://www.kodility.com/forgotMyPassword/reset?token=token_123"
    }

    def "should check for valid token"() {
        given:
        def foundToken = new Token(id: 1L, tokenType: TokenType.FORGOT_MY_PASSWORD, token: "test_token")
        1 * tokenService.findBy("test_token", TokenType.FORGOT_MY_PASSWORD) >> foundToken

        expect:
        service.validPasswordResetToken("test_token")
    }

    def "should return false if token does not exists"() {
        given:
        1 * tokenService.findBy("different_token", TokenType.FORGOT_MY_PASSWORD) >> null

        expect:
        !service.validPasswordResetToken("different_token")
    }

    def "should delete token"() {
        when:
        service.deleteToken("tokenToBeDeleted")

        then:
        1 * tokenService.deleteToken("tokenToBeDeleted", TokenType.FORGOT_MY_PASSWORD)
    }

}
