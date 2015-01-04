package com.kodility.service.user;

import com.kodility.domain.token.Token;
import com.kodility.domain.token.TokenType;
import com.kodility.domain.user.User;
import com.kodility.service.email.EmailService;
import com.kodility.service.email.model.Email;
import com.kodility.service.util.TokenService;
import com.kodility.service.util.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ForgotMyPasswordService {

    private static final String PASSWORD_SERVICE_EMAIL = "passwordservice@kodility.com";

    @Autowired
    private EmailService emailService;

    @Autowired
    private UrlService urlService;

    @Autowired
    private TokenService tokenService;

    public void sendResetEmail(User user) {
        String token = tokenService.createTokenFor(user, TokenType.FORGOT_MY_PASSWORD);
        String resetUrl = urlService.createUrlFor("/forgotMyPassword/reset?token=" + token);
        Email email = prepareEmailFor(user);
        Map<String, Object> params = prepareEmailParameters(user, resetUrl);
        emailService.send(email, params);
    }

    public boolean validPasswordResetToken(String token) {
        return getForgotMyPasswordTokenFor(token) != null;
    }

    public Token getForgotMyPasswordTokenFor(String token) {
        return tokenService.findBy(token, TokenType.FORGOT_MY_PASSWORD);
    }

    private Email prepareEmailFor(User user) {
        return new Email().setTo(user.getEmail())
                .setFrom(PASSWORD_SERVICE_EMAIL)
                .setSubjectKey("forgotmypassword.subject")
                .setTemplateName("forgotmypassword_email");
    }

    private Map<String, Object> prepareEmailParameters(User user, String resetUrl) {
        Map<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("url", resetUrl);
        return params;
    }

    public void deleteToken(String token) {
        tokenService.deleteToken(token, TokenType.FORGOT_MY_PASSWORD);
    }

}
