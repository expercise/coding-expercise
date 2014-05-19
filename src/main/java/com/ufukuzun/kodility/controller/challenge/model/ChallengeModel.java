package com.ufukuzun.kodility.controller.challenge.model;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.ChallengeInputType;
import com.ufukuzun.kodility.domain.challenge.TestCaseInputValue;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.enums.Lingo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChallengeModel {

    private Long challengeId;

    private List<MultiLingoText> titles = new ArrayList<>();

    private List<MultiLingoText> descriptions = new ArrayList<>();

    private List<DataType> inputTypes = new ArrayList<>();

    private DataType outputType;

    private List<TestCase> testCases = new ArrayList<>();

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public List<MultiLingoText> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<MultiLingoText> descriptions) {
        this.descriptions = descriptions;
    }

    public List<MultiLingoText> getTitles() {
        return titles;
    }

    public void setTitles(List<MultiLingoText> titles) {
        this.titles = titles;
    }

    public List<DataType> getInputTypes() {
        return inputTypes;
    }

    public void setInputTypes(List<DataType> inputTypes) {
        this.inputTypes = inputTypes;
    }

    public DataType getOutputType() {
        return outputType;
    }

    public boolean hasOutputType() {
        return outputType != null;
    }

    public void setOutputType(DataType outputType) {
        this.outputType = outputType;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    public String getTitleFor(Lingo lingo) {
        return filterByLingo(lingo, titles);
    }

    public String getDescriptionFor(Lingo lingo) {
        return filterByLingo(lingo, descriptions);
    }

    private String filterByLingo(Lingo lingo, List<MultiLingoText> descriptions1) {
        for (MultiLingoText multiLingoText : descriptions1) {
            if (lingo == multiLingoText.getLingo()) {
                return multiLingoText.getText();
            }
        }
        return StringUtils.EMPTY;
    }

    public Challenge createChallenge() {
        Challenge challenge = new Challenge();
        mergeChallengeWithModel(challenge);
        return challenge;
    }

    public void mergeChallengeWithModel(Challenge challenge) {
        challenge.getTitles().clear();
        for (MultiLingoText title : titles) {
            challenge.getTitles().put(title.getLingo(), title.getText());
        }

        challenge.getDescriptions().clear();
        for (MultiLingoText description : descriptions) {
            challenge.getDescriptions().put(description.getLingo(), description.getText());
        }

        challenge.getInputTypes().clear();
        challenge.setInputTypes(ChallengeInputType.createFrom(inputTypes));

        challenge.setOutputType(outputType);

        challenge.getTestCases().clear();
        for (TestCase testCase : testCases) {
            challenge.addTestCase(testCase.getInputValues(), testCase.outputValue);
        }
    }

    public static ChallengeModel createFrom(Challenge challenge) {
        ChallengeModel challengeModel = new ChallengeModel();

        challengeModel.setChallengeId(challenge.getId());

        for (Map.Entry<Lingo, String> eachTitle : challenge.getTitles().entrySet()) {
            challengeModel.titles.add(new MultiLingoText(eachTitle.getKey(), eachTitle.getValue()));
        }

        for (Map.Entry<Lingo, String> eachDescription : challenge.getDescriptions().entrySet()) {
            challengeModel.descriptions.add(new MultiLingoText(eachDescription.getKey(), eachDescription.getValue()));
        }

        for (ChallengeInputType eachInputType : challenge.getInputTypes()) {
            challengeModel.inputTypes.add(eachInputType.getInputType());
        }

        for (com.ufukuzun.kodility.domain.challenge.TestCase eachTestCase : challenge.getTestCases()) {
            TestCase testCase = new TestCase();
            for (TestCaseInputValue eachInputValue : eachTestCase.getInputs()) {
                testCase.getInputValues().add(eachInputValue.getInputValue());
            }
            testCase.setOutputValue(eachTestCase.getOutput());
            challengeModel.getTestCases().add(testCase);
        }

        challengeModel.setOutputType(challenge.getOutputType());

        return challengeModel;
    }

    public static class MultiLingoText {

        private Lingo lingo;

        private String text;

        public MultiLingoText() {
        }

        public MultiLingoText(Lingo lingo, String text) {
            this.lingo = lingo;
            this.text = text;
        }

        public Lingo getLingo() {
            return lingo;
        }

        public void setLingo(Lingo lingo) {
            this.lingo = lingo;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

    public static class TestCase {

        private List<String> inputValues = new ArrayList<>();

        private String outputValue;

        public List<String> getInputValues() {
            return inputValues;
        }

        public void setInputValues(List<String> inputValues) {
            this.inputValues = inputValues;
        }

        public String getOutputValue() {
            return outputValue;
        }

        public void setOutputValue(String outputValue) {
            this.outputValue = outputValue;
        }

    }

}
