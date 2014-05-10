package com.ufukuzun.kodility.controller.challenge.model;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.enums.Lingo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SaveChallengeRequest {

    private List<MultiLingoText> titles = new ArrayList<>();

    private List<MultiLingoText> descriptions = new ArrayList<>();

    private List<DataType> inputTypes = new ArrayList<>();

    private DataType outputType;

    private List<TestCase> testCases = new ArrayList<>();

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

        for (MultiLingoText title : titles) {
            challenge.getTitles().put(title.getLingo(), title.getText());
        }

        for (MultiLingoText description : descriptions) {
            challenge.getDescriptions().put(description.getLingo(), description.getText());
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

    public static class MultiLingoText {

        private Lingo lingo;

        private String text;

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
