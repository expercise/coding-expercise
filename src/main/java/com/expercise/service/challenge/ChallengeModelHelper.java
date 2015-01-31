package com.expercise.service.challenge;

import com.expercise.controller.challenge.model.ChallengeModel;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.ChallengeInputType;
import com.expercise.domain.challenge.TestCase;
import com.expercise.domain.challenge.TestCaseInputValue;
import com.expercise.enums.Lingo;
import com.expercise.service.level.LevelService;
import com.expercise.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChallengeModelHelper {

    @Autowired
    private LevelService levelService;

    @Autowired
    private AuthenticationService authenticationService;

    public Challenge createChallengeFrom(ChallengeModel challengeModel) {
        Challenge challenge = new Challenge();
        mergeChallengeWithModel(challengeModel, challenge);
        return challenge;
    }

    public void mergeChallengeWithModel(ChallengeModel challengeModel, Challenge challenge) {
        challenge.getTitles().clear();
        for (ChallengeModel.MultiLingoText title : challengeModel.getTitles()) {
            challenge.getTitles().put(title.getLingo(), title.getText());
        }

        challenge.getDescriptions().clear();
        for (ChallengeModel.MultiLingoText description : challengeModel.getDescriptions()) {
            challenge.getDescriptions().put(description.getLingo(), description.getText());
        }

        challenge.getInputTypes().clear();
        challenge.setInputTypes(ChallengeInputType.createFrom(challengeModel.getInputTypes()));

        challenge.setOutputType(challengeModel.getOutputType());

        challenge.getTestCases().clear();
        for (ChallengeModel.TestCase testCase : challengeModel.getTestCases()) {
            challenge.addTestCase(testCase.getInputValues(), testCase.getOutputValue());
        }

        if (challengeModel.getApproved() != null) {
            challenge.setApproved(challengeModel.getApproved());
        }

        Long levelId = challengeModel.getLevel();
        if (levelId != null) {
            challenge.setLevel(levelService.findById(levelId));
        } else if (authenticationService.isCurrentUserAdmin()) {
            challenge.setLevel(null);
        }
    }

    public ChallengeModel createModelFrom(Challenge challenge) {
        ChallengeModel challengeModel = new ChallengeModel();

        challengeModel.setChallengeId(challenge.getId());

        for (Map.Entry<Lingo, String> eachTitle : challenge.getTitles().entrySet()) {
            challengeModel.getTitles().add(new ChallengeModel.MultiLingoText(eachTitle.getKey(), eachTitle.getValue()));
        }

        for (Map.Entry<Lingo, String> eachDescription : challenge.getDescriptions().entrySet()) {
            challengeModel.getDescriptions().add(new ChallengeModel.MultiLingoText(eachDescription.getKey(), eachDescription.getValue()));
        }

        for (ChallengeInputType eachInputType : challenge.getInputTypes()) {
            challengeModel.getInputTypes().add(eachInputType.getInputType());
        }

        for (TestCase eachTestCase : challenge.getTestCases()) {
            ChallengeModel.TestCase testCase = new ChallengeModel.TestCase();
            for (TestCaseInputValue eachInputValue : eachTestCase.getInputs()) {
                testCase.getInputValues().add(eachInputValue.getInputValue());
            }
            testCase.setOutputValue(eachTestCase.getOutput());
            challengeModel.getTestCases().add(testCase);
        }

        challengeModel.setOutputType(challenge.getOutputType());

        challengeModel.setApproved(challenge.isApproved());

        if (challenge.hasLevel()) {
            challengeModel.setLevel(challenge.getLevelId());
        }

        return challengeModel;
    }

}
