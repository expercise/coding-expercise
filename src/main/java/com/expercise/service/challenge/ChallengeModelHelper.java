package com.expercise.service.challenge;

import com.expercise.controller.challenge.model.ChallengeModel;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.ChallengeInputType;
import com.expercise.domain.challenge.TestCase;
import com.expercise.domain.challenge.TestCaseInputValue;
import com.expercise.enums.Lingo;
import com.expercise.utils.JsonUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChallengeModelHelper {

    public Challenge createChallengeFrom(ChallengeModel challengeModel) {
        Challenge challenge = new Challenge();
        mergeChallengeWithModel(challengeModel, challenge);
        return challenge;
    }

    public void mergeChallengeWithModel(ChallengeModel challengeModel, Challenge challenge) {
        challenge.setChallengeType(challengeModel.getChallengeType());

        challenge.getTitles().clear();
        for (ChallengeModel.MultiLingoText title : challengeModel.getTitles()) {
            challenge.getTitles().put(title.getLingo(), title.getText());
        }

        challenge.getDescriptions().clear();
        for (ChallengeModel.MultiLingoText description : challengeModel.getDescriptions()) {
            challenge.getDescriptions().put(description.getLingo(), description.getText());
        }

        challenge.getSignatures().clear();
        for (ChallengeModel.MultiLingoText signature : challengeModel.getSignatures()) {
            challenge.getSignatures().put(signature.getLingo(), signature.getText());
        }

        challenge.getInputTypes().clear();
        challenge.setInputTypes(ChallengeInputType.createFrom(challengeModel.getInputTypes()));

        challenge.setOutputType(challengeModel.getOutputType());

        challenge.getTestCases().clear();
        for (ChallengeModel.TestCase testCase : challengeModel.getTestCases()) {
            challenge.addTestCase(
                    testCase.getInputValues().stream().map(JsonUtils::format).collect(Collectors.toList()),
                    JsonUtils.format(testCase.getOutputValue())
            );
        }
        TestCase.prioritize(challenge.getTestCases());

        if (challengeModel.getApproved() != null) {
            challenge.setApproved(challengeModel.getApproved());
        }
    }

    public ChallengeModel createModelFrom(Challenge challenge) {
        ChallengeModel challengeModel = new ChallengeModel();

        challengeModel.setChallengeId(challenge.getId());

        challengeModel.setChallengeType(challenge.getChallengeType());

        for (Map.Entry<Lingo, String> eachTitle : challenge.getTitles().entrySet()) {
            challengeModel.getTitles().add(new ChallengeModel.MultiLingoText(eachTitle.getKey(), eachTitle.getValue()));
        }

        for (Map.Entry<Lingo, String> eachDescription : challenge.getDescriptions().entrySet()) {
            challengeModel.getDescriptions().add(new ChallengeModel.MultiLingoText(eachDescription.getKey(), eachDescription.getValue()));
        }

        for (Map.Entry<Lingo, String> eachSignature : challenge.getSignatures().entrySet()) {
            challengeModel.getSignatures().add(new ChallengeModel.MultiLingoText(eachSignature.getKey(), eachSignature.getValue()));
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

        return challengeModel;
    }

}
