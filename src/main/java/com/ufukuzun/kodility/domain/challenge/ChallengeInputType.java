package com.ufukuzun.kodility.domain.challenge;

import com.ufukuzun.kodility.domain.PrioritisedEntity;
import com.ufukuzun.kodility.enums.DataType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ChallengeInputType extends PrioritisedEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private DataType inputType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        PrioritisedEntity.prioritise(challengeInputTypes);
        return challengeInputTypes;
    }

}
