package com.expercise.service.user

import com.expercise.domain.token.Token
import com.expercise.domain.token.TokenType
import com.expercise.domain.user.User
import com.expercise.service.email.EmailService
import com.expercise.service.email.model.Email
import com.expercise.service.util.TokenService
import com.expercise.service.util.UrlService
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
        User user = new User(id: 1L, email: "user@expercise.com", firstName: "Ahmet", lastName: "Mehmet")
        1 * tokenService.createTokenFor(user, TokenType.FORGOT_MY_PASSWORD) >> "token_123"
        1 * urlService.createUrlFor("/forgotMyPassword/reset?token=token_123") >> "http://coding.expercise.com/forgotMyPassword/reset?token=token_123"

        when:
        service.sendResetEmail(user)

        then: "verify email content and parameters"
        1 * emailService.send({ emailCaptor = it } as Email, { paramCaptor = it } as Map)
        emailCaptor.to == "user@expercise.com"
        emailCaptor.from == "passwordservice@expercise.com"
        emailCaptor.subjectKey == "forgotMyPassword.subject"
        emailCaptor.templateName == "forgotMyPasswordEmail"

        and: "verify email parameters"
        paramCaptor.user == user
        paramCaptor.url == "http://coding.expercise.com/forgotMyPassword/reset?token=token_123"
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
