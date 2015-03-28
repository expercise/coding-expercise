package com.expercise.controller.user

import com.expercise.controller.user.model.EmailRequestModel
import com.expercise.controller.user.model.ForgotMyPasswordResponse
import com.expercise.controller.user.model.PasswordModel
import com.expercise.controller.user.model.PasswordResetModel
import com.expercise.domain.token.Token
import com.expercise.domain.token.TokenType
import com.expercise.domain.user.User
import com.expercise.service.user.ForgotMyPasswordService
import com.expercise.service.user.UserService
import org.springframework.validation.BindingResult
import org.springframework.web.servlet.ModelAndView
import spock.lang.Specification

class ForgotMyPasswordControllerSpec extends Specification {

    ForgotMyPasswordController controller

    ForgotMyPasswordService forgotMyPasswordService = Mock()
    UserService userService = Mock()
    BindingResult bindingResult = Mock()

    def setup() {
        def dependencies = [userService: userService, forgotMyPasswordService: forgotMyPasswordService]
        controller = new ForgotMyPasswordController(dependencies)
    }

    def "should show forgot password page"() {
        expect:
        controller.index().getViewName() == "/user/forgotMyPassword"
    }

    def "should respond with success message to forgot my password request with proper email for a user"() {
        given:
        User forgetfulUser = new User(id: 1L, email: "user@expercise.com")
        1 * userService.findByEmail("user@expercise.com") >> forgetfulUser
        1 * forgotMyPasswordService.sendResetEmail(forgetfulUser)

        when:
        def emailRequest = new EmailRequestModel(email: "user@expercise.com")
        ForgotMyPasswordResponse response = controller.sendForgotMyPasswordEmail(emailRequest)

        then:
        response.success
        response.messageKey == "forgotMyPassword.request.success"
    }

    def "should respond with failed message to forgot my password request if email is invalid"() {
        given:
        1 * userService.findByEmail("invalidUser@expercise.com") >> null
        0 * forgotMyPasswordService._

        when:
        def emailRequest = new EmailRequestModel(email: "invalidUser@expercise.com")
        ForgotMyPasswordResponse response = controller.sendForgotMyPasswordEmail(emailRequest)

        then:
        !response.success
        response.messageKey == "forgotMyPassword.request.failed"
    }

    def "should show password reset page if token is valid"() {
        given:
        1 * forgotMyPasswordService.validPasswordResetToken("token_valid_123") >> true

        when:
        def mav = controller.resetPasswordIndex("token_valid_123")

        then:
        mav.getViewName() == "/user/passwordReset"
    }

    def "should redirect to homepage if token is valid"() {
        given:
        1 * forgotMyPasswordService.validPasswordResetToken("invalid_token") >> false

        when:
        def mav = controller.resetPasswordIndex("invalid_token")

        then:
        mav.getViewName() == "redirect:/"
    }

    def "should redirect to same page if model has error"() {
        given:
        def passwordResetModel = new PasswordResetModel()
        1 * bindingResult.hasErrors() >> true

        when:
        ModelAndView mav = controller.resetPassword(passwordResetModel, bindingResult)

        then:
        mav.getViewName() == "/user/passwordReset"
    }

    def "should check token and redirect to home page after deleted the token if token is not valid"() {
        given:
        def passwordModel = new PasswordModel(password: "password123qwe", passwordRetype: "password123qwe")
        def passwordResetModel = new PasswordResetModel(token: "invalidToken", passwordModel: passwordModel)
        1 * bindingResult.hasErrors() >> false
        1 * forgotMyPasswordService.getForgotMyPasswordTokenFor("invalidToken") >> null

        when:
        ModelAndView mav = controller.resetPassword(passwordResetModel, bindingResult)

        then:
        mav.getViewName() == "redirect:/"
        1 * forgotMyPasswordService.deleteToken("invalidToken")
    }

    def "should check token and redirect to login page with parameter if password changed"() {
        given:
        def passwordModel = new PasswordModel(password: "password123qwe", passwordRetype: "password123qwe")
        def passwordResetModel = new PasswordResetModel(token: "token_123", passwordModel: passwordModel)
        def user = new User(id: 2L)
        def foundToken = new Token(id: 1L, user: user, token: "token_123", tokenType: TokenType.FORGOT_MY_PASSWORD)

        and: "interactions"
        1 * bindingResult.hasErrors() >> false
        1 * forgotMyPasswordService.getForgotMyPasswordTokenFor("token_123") >> foundToken

        when:
        ModelAndView mav = controller.resetPassword(passwordResetModel, bindingResult)

        then:
        mav.getViewName() == "redirect:/login?resetPasswordSuccess"
        1 * userService.saveNewUser({ it.password == "password123qwe" })

        then:
        1 * forgotMyPasswordService.deleteToken("token_123")
    }

}
