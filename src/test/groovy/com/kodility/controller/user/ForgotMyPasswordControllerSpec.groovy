package com.kodility.controller.user
import com.kodility.controller.user.model.EmailRequestModel
import com.kodility.controller.user.model.ForgotMyPasswordResponse
import com.kodility.domain.user.User
import com.kodility.service.user.ForgotMyPasswordService
import com.kodility.service.user.UserService
import spock.lang.Specification

class ForgotMyPasswordControllerSpec extends Specification {

    ForgotMyPasswordService forgotMyPasswordService = Mock()
    UserService userService = Mock()
    ForgotMyPasswordController controller

    def setup() {
        def dependencies = [userService: userService, forgotMyPasswordService: forgotMyPasswordService]
        controller = new ForgotMyPasswordController(dependencies)
    }

    def "should show forgot password page"() {
        expect: controller.index().getViewName() == "/user/forgotMyPassword"
    }

    def "should respond with success message to forgot my password request with proper email for a user"() {
        setup:
        User forgetfulUser = new User(id: 1L, email: "user@kodility.com")
        1 * userService.findByEmail("user@kodility.com") >> forgetfulUser
        1 * forgotMyPasswordService.sendResetEmail(forgetfulUser)
        when:
        def emailRequest = new EmailRequestModel(email: "user@kodility.com")
        ForgotMyPasswordResponse response = controller.sendForgotMyPasswordEmail(emailRequest)
        then:
        response.success
        response.messageKey == "forgotMyPassword.request.success"
    }

    def "should respond with failed message to forgot my password request if email is invalid"() {
        setup:
        1 * userService.findByEmail("invalidUser@kodility.com") >> null
        0 * forgotMyPasswordService._
        when:
        def emailRequest = new EmailRequestModel(email: "invalidUser@kodility.com")
        ForgotMyPasswordResponse response = controller.sendForgotMyPasswordEmail(emailRequest)
        then:
        !response.success
        response.messageKey == "forgotMyPassword.request.failed"
    }

}
