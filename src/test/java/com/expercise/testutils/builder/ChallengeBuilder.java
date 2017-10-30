package com.expercise.testutils.builder;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.ChallengeType;
import com.expercise.domain.challenge.TestCase;
import com.expercise.domain.user.User;
import com.expercise.enums.DataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChallengeBuilder extends BaseEntityBuilder<Challenge, ChallengeBuilder> {

    private boolean approved = true;

    private ChallengeType challengeType;

    private User user;

    private int point = 1;

    private DataType outputType = DataType.Integer;

    private List<TestCase> testCases = new ArrayList<>();

    @Override
    protected Challenge doBuild() {
        Challenge challenge = new Challenge();
        challenge.setChallengeType(challengeType);
        challenge.setApproved(approved);
        challenge.setUser(user);
        challenge.setPoint(point);
        challenge.setOutputType(outputType);
        testCases.forEach(challenge::addTestCase);
        return challenge;
    }

    public ChallengeBuilder challengeType(ChallengeType challengeType) {
        this.challengeType = challengeType;
        return this;
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

    public ChallengeBuilder outputType(DataType outputType) {
        this.outputType = outputType;
        return this;
    }

    public ChallengeBuilder testCases(TestCase... testCases) {
        Collections.addAll(this.testCases, testCases);
        return this;
    }

}
