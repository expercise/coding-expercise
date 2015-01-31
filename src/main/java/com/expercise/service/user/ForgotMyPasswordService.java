package com.expercise.service.user;

import com.expercise.domain.token.Token;
import com.expercise.domain.token.TokenType;
import com.expercise.domain.user.User;
import com.expercise.service.email.EmailService;
import com.expercise.service.email.model.Email;
import com.expercise.service.util.TokenService;
import com.expercise.service.util.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ForgotMyPasswordService {

    private static final String PASSWORD_SERVICE_EMAIL = "passwordservice@expercise.com";

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
                .setSubjectKey("forgotMyPassword.subject")
                .setTemplateName("forgotMyPasswordEmail");
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
