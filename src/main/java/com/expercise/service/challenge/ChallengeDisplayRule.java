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
        User currentUser = authenticationService.getCurrentUser();
        if (currentUser.isAdmin()) {
            return false;
        }

        if (challenge.isNotApproved()) {
            return challenge.isNotAuthor(currentUser);
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
