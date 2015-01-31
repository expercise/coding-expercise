package com.expercise.service.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.level.Level;
import com.expercise.domain.user.User;
import com.expercise.service.challenge.model.CurrentLevelModel;
import com.expercise.service.level.CurrentLevelHelper;
import com.expercise.service.user.AuthenticationService;
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
