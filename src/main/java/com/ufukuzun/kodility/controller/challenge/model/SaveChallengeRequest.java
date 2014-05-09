package com.ufukuzun.kodility.controller.challenge.model;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.enums.Lingo;

import java.util.ArrayList;
import java.util.List;

public class SaveChallengeRequest {

    private List<Title> titles = new ArrayList<>();

    private List<Description> descriptions = new ArrayList<>();

    private List<DataType> inputTypes = new ArrayList<>();

    private DataType outputType;

    private List<TestCase> testCases = new ArrayList<>();

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
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

    public void setOutputType(DataType outputType) {
        this.outputType = outputType;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    public Challenge createChallenge() {
        Challenge challenge = new Challenge();

        for (Title title : titles) {
            challenge.getTitles().put(title.getLingo(), title.getTitle());
        }

        for (Description description : descriptions) {
            challenge.getDescriptions().put(description.getLingo(), description.getDescription());
        }

        challenge.setInputTypes(inputTypes);

        challenge.setOutputType(outputType);

        for (TestCase testCase : testCases) {
            List<Object> inputs = new ArrayList<>();
            for (int inputType = 0; inputType < inputTypes.size(); inputType++) {
                inputs.add(inputTypes.get(inputType).convert(testCase.getInputValues().get(inputType)));
            }

            challenge.addTestCase(inputs, outputType.convert(testCase.outputValue));
        }

        return challenge;
    }

    public static class Title {

        private Lingo lingo;

        private String title;

        public Lingo getLingo() {
            return lingo;
        }

        public void setLingo(Lingo lingo) {
            this.lingo = lingo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }

    public static class Description {

        private Lingo lingo;

        private String description;

        public Lingo getLingo() {
            return lingo;
        }

        public void setLingo(Lingo lingo) {
            this.lingo = lingo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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
