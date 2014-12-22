package com.kodility.service.challenge;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.level.Level;
import com.kodility.domain.user.User;
import com.kodility.service.challenge.model.CurrentLevelModel;
import com.kodility.service.level.CurrentLevelHelper;
import com.kodility.service.user.AuthenticationService;
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

        if (challenge.hasNotLevel()) {
            return false;
        }

        Level level = challenge.getLevel();
        if (level.hasTheme()) {
            CurrentLevelModel currentLevelModel = currentLevelHelper.prepareCurrentLevelModelFor(currentUser, level.getTheme().getOrderedLevels());
            return currentLevelModel.isLevelDeactive(level);
        }

        return false;
    }

}
