package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.service.challenge.model.CurrentLevelModel;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChallengeDisplayRule {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CurrentLevelHelper currentLevelHelper;

    public boolean isNotDisplayable(Challenge challenge) {
        if (challenge == null) {
            return true;
        }

        User currentUser = authenticationService.getCurrentUser();
        if (challenge.isNotApproved()) {
            return challenge.isNotAuthor(currentUser) && currentUser.isNotAdmin();
        }

        CurrentLevelModel currentLevelModel = currentLevelHelper.prepareCurrentLevelModelFor(currentUser);
        if (challenge.hasLevel() && currentLevelModel.isLevelDeactive(challenge.getLevel())) {
            return true;
        }

        return false;
    }

}
