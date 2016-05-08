package com.expercise.service.user;

import com.expercise.configuration.SpringSecurityConfiguration;
import com.expercise.domain.user.User;
import com.expercise.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class SocialSignInAdapter implements SignInAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest nativeWebRequest) {
        if (authenticationService.isCurrentUserAuthenticated()) {
            if (isMigrationFromSocialUserToCurrentUserNeeded(userId)) {
                userService.saveSocialUser(connection.fetchUserProfile(), connection.createData());
            }
            return "/user";
        } else {
            User user = userService.findById(NumberUtils.parseLong(userId));
            authenticationService.authenticate(connection, user);

            return prepareRedirectUrl(nativeWebRequest);
        }
    }

    private boolean isMigrationFromSocialUserToCurrentUserNeeded(String userId) {
        return !authenticationService.getCurrentUser().getId().toString().equals(userId);
    }

    private String prepareRedirectUrl(NativeWebRequest nativeWebRequest) {
        HttpSession session = nativeWebRequest.getNativeRequest(HttpServletRequest.class).getSession(false);
        if (session != null) {
            SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
            if (savedRequest != null) {
                return savedRequest.getRedirectUrl();
            }
        }

        return SpringSecurityConfiguration.DEFAULT_SUCCESS_SIGNIN_URL;
    }

}
