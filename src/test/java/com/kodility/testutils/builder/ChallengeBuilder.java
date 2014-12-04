package com.kodility.testutils.builder;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.Level;
import com.kodility.domain.user.User;

public class ChallengeBuilder extends AbstractEntityBuilder<Challenge, ChallengeBuilder> {

    private boolean approved = true;

    private User user;

    private int point = 1;

    private Level level;

    @Override
    protected Challenge doBuild() {
        Challenge challenge = new Challenge();
        challenge.setApproved(approved);
        challenge.setUser(user);
        challenge.setPoint(point);
        challenge.setLevel(level);
        if (level != null) {
            level.getChallenges().add(challenge);
        }
        return challenge;
    }

    public ChallengeBuilder approved(boolean approved) {
        this.approved = approved;
        return this;
    }

    public ChallengeBuilder user(User user) {
        this.user = user;
        return this;
    }

    public ChallengeBuilder point(int point) {
        this.point = point;
        return this;
    }

    public ChallengeBuilder level(Level level) {
        this.level = level;
        return this;
    }

}
