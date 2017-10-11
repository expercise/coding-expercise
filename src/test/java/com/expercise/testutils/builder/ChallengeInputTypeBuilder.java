package com.expercise.testutils.builder;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.ChallengeInputType;
import com.expercise.enums.DataType;

public class ChallengeInputTypeBuilder extends BasePrioritizedEntityBuilder<ChallengeInputType, ChallengeInputTypeBuilder> {

    private DataType inputType;

    private Challenge challenge;

    @Override
    protected ChallengeInputType getInstance() {
        ChallengeInputType challengeInputType = new ChallengeInputType();
        challengeInputType.setChallenge(challenge);
        challengeInputType.setInputType(inputType);
        challenge.getInputTypes().add(challengeInputType);
        return challengeInputType;
    }

    public ChallengeInputTypeBuilder inputType(DataType inputType) {
        this.inputType = inputType;
        return this;
    }

    public ChallengeInputTypeBuilder challenge(Challenge challenge) {
        this.challenge = challenge;
        return this;
    }

}
