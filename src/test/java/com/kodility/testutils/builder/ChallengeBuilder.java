package com.kodility.testutils.builder;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.level.Level;
import com.kodility.domain.user.User;
import com.kodility.enums.DataType;
import com.kodility.utils.collection.RandomElement;

public class ChallengeBuilder extends AbstractEntityBuilder<Challenge, ChallengeBuilder> {

    private boolean approved = true;

    private User user;

    private int point = 1;

    private Level level;

    private DataType outputType = RandomElement.from(DataType.values());

    @Override
    protected Challenge doBuild() {

        Challenge challenge = new Challenge();
        challenge.setApproved(approved);
        challenge.setUser(user);
        challenge.setPoint(point);
        challenge.setLevel(level);
        challenge.setOutputType(outputType);
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

    public ChallengeBuilder outputType(DataType outputType) {
        this.outputType = outputType;
        return this;
    }

}
