package com.expercise.interpreter;

import com.expercise.domain.challenge.TestCase;

import java.io.Serializable;

public class TestCaseWithResult implements Serializable {

    private TestCase testCaseUnderTest;

    private TestCaseResult testCaseResult;

    private String actualValue;

    public TestCaseWithResult(TestCase testCaseUnderTest) {
        this.testCaseUnderTest = testCaseUnderTest;
        this.testCaseResult = TestCaseResult.NEW;
    }

    public TestCase getTestCaseUnderTest() {
        return testCaseUnderTest;
    }

    public TestCaseResult getTestCaseResult() {
        return testCaseResult;
    }

    public void setTestCaseResult(TestCaseResult testCaseResult) {
        this.testCaseResult = testCaseResult;
    }

    public String getExpectedValue() {
        return getTestCaseUnderTest().getOutput();
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    public boolean isFailed() {
        return getTestCaseResult() == TestCaseResult.FAILED;
    }

}
