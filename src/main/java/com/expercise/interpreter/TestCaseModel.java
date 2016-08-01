package com.expercise.interpreter;

import com.expercise.domain.challenge.ChallengeInputType;
import com.expercise.domain.challenge.TestCase;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestCaseModel implements Serializable {

    private static final long serialVersionUID = -5656146862340963922L;

    private List<String> inputs = new ArrayList<>();

    private String output;

    private String actualValue;

    private TestCaseResult testCaseResult;

    private String resultMessage = StringUtils.EMPTY;

    public static TestCaseModel createFrom(TestCaseWithResult testCaseWithResult) {
        TestCaseModel testCaseModel = new TestCaseModel();
        TestCase testCaseUnderTest = testCaseWithResult.getTestCaseUnderTest();
        List<ChallengeInputType> inputTypes = testCaseUnderTest.getChallenge().getInputTypes();
        for (int i = 0; i < inputTypes.size(); i++) {
            String inputValue = testCaseUnderTest.getInputs().get(i).getInputValue();
            testCaseModel.getInputs().add(inputValue);
        }

        testCaseModel.setOutput(testCaseUnderTest.getOutput());
        testCaseModel.setActualValue(testCaseWithResult.getActualValue());
        testCaseModel.setTestCaseResult(testCaseWithResult.getTestCaseResult());
        testCaseModel.setResultMessage(testCaseWithResult.getResultMessage());
        return testCaseModel;
    }

    public List<String> getInputs() {
        return inputs;
    }

    public void setInputs(List<String> inputs) {
        this.inputs = inputs;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    public TestCaseResult getTestCaseResult() {
        return testCaseResult;
    }

    public void setTestCaseResult(TestCaseResult testCaseResult) {
        this.testCaseResult = testCaseResult;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
