package com.ufukuzun.kodility.testutils.builder;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.user.User;

public class ChallengeBuilder extends AbstractEntityBuilder<Challenge, ChallengeBuilder> {

    private boolean approved;

    private User user;

    @Override
    public Challenge doBuild() {
        Challenge challenge = new Challenge();
        challenge.setApproved(approved);
        challenge.setUser(user);
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

}
