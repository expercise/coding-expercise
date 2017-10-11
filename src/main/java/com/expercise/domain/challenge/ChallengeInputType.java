package com.expercise.domain.challenge;

import com.expercise.domain.PrioritizedEntity;
import com.expercise.enums.DataType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "SEQ_CHALLENGE_INPUT_TYPE")
public class ChallengeInputType extends PrioritizedEntity {

    @Enumerated(EnumType.STRING)
    private DataType inputType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;

    public DataType getInputType() {
        return inputType;
    }

    public void setInputType(DataType inputType) {
        this.inputType = inputType;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public static List<ChallengeInputType> createFrom(List<DataType> inputTypes) {
        List<ChallengeInputType> challengeInputTypes = new ArrayList<>();
        for (DataType inputType : inputTypes) {
            ChallengeInputType challengeInputType = new ChallengeInputType();
            challengeInputType.setInputType(inputType);
            challengeInputTypes.add(challengeInputType);
        }
        prioritize(challengeInputTypes);
        return challengeInputTypes;
    }

}
