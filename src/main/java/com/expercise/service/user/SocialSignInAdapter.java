package com.expercise.service.user;

import com.expercise.domain.user.User;
import com.expercise.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

@Service
public class SocialSignInAdapter implements SignInAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        if (authenticationService.isCurrentUserAuthenticated()) {
            if (isMigrationFromSocialUserToCurrentUserNeeded(userId)) {
                userService.saveSocialUser(connection.fetchUserProfile(), connection.createData());
            }
            return "/user";
        } else {
            User user = userService.findById(NumberUtils.parseLong(userId));
            authenticationService.authenticate(connection, user);
            // TODO ufuk: redirect to requested url
            return "/themes";
        }
    }

    private boolean isMigrationFromSocialUserToCurrentUserNeeded(String userId) {
        return !authenticationService.getCurrentUser().getId().toString().equals(userId);
    }

}
