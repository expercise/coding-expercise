package com.expercise.interpreter;

import com.expercise.domain.challenge.TestCase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestCaseModel implements Serializable {

    private List<String> inputs = new ArrayList<>();

    private String output;

    private String actualValue;

    private TestCaseResult testCaseResult;

    public static TestCaseModel createFrom(TestCaseWithResult testCaseWithResult) {
        TestCaseModel testCaseModel = new TestCaseModel();
        TestCase testCaseUnderTest = testCaseWithResult.getTestCaseUnderTest();
        testCaseUnderTest.getInputs().stream().forEach(
                testCaseInputValue -> testCaseModel.getInputs().add(testCaseInputValue.getInputValue())
        );
        testCaseModel.setOutput(testCaseUnderTest.getOutput());
        testCaseModel.setActualValue(testCaseWithResult.getActualValue());
        testCaseModel.setTestCaseResult(testCaseWithResult.getTestCaseResult());
        return testCaseModel;
    }

    public List<String> getInputs() {
        return inputs;
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

    public void setTestCaseResult(TestCaseResult testCaseResult) {
        this.testCaseResult = testCaseResult;
    }

}
