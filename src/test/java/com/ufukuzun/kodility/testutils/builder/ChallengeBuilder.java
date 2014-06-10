package com.ufukuzun.kodility.testutils.builder;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.user.User;

public class ChallengeBuilder extends AbstractEntityBuilder<Challenge, ChallengeBuilder> {

    private boolean approved = true;

    private User user;

    private int point = 1;

    @Override
    public Challenge doBuild() {
        Challenge challenge = new Challenge();
        challenge.setApproved(approved);
        challenge.setUser(user);
        challenge.setPoint(point);
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

}
