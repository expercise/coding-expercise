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

    public TestCaseModel(TestCaseWithResult testCaseWithResult) {
        TestCase testCaseUnderTest = testCaseWithResult.getTestCaseUnderTest();
        List<ChallengeInputType> inputTypes = testCaseUnderTest.getChallenge().getInputTypes();
        for (int i = 0; i < inputTypes.size(); i++) {
            String inputValue = testCaseUnderTest.getInputs().get(i).getInputValue();
            this.inputs.add(inputValue);
        }
        this.output = testCaseUnderTest.getOutput();
        this.actualValue = testCaseWithResult.getActualValue();
        this.testCaseResult = testCaseWithResult.getTestCaseResult();
        this.resultMessage = testCaseWithResult.getResultMessage();
    }

    public List<String> getInputs() {
        return inputs;
    }

    public String getOutput() {
        return output;
    }

    public String getActualValue() {
        return actualValue;
    }

    public TestCaseResult getTestCaseResult() {
        return testCaseResult;
    }

    public String getResultMessage() {
        return resultMessage;
    }

}
