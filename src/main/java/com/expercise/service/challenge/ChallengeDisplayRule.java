package com.expercise.service.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.user.User;
import com.expercise.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChallengeDisplayRule {

    @Autowired
    private AuthenticationService authenticationService;

    public boolean isNotDisplayable(Challenge challenge) {
        if (!authenticationService.isCurrentUserAuthenticated() && challenge.isApproved()) {
            return false;
        }

        User currentUser = authenticationService.getCurrentUser();

        if (currentUser.isAdmin()) {
            return false;
        }

        if (challenge.isNotApproved()) {
            return challenge.isNotAuthor(currentUser);
        }

        return false;
    }

}
