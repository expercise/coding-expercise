package com.expercise.interpreter;

import com.expercise.domain.challenge.TestCase;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class TestCaseWithResult implements Serializable {

    private static final long serialVersionUID = 3004304070142162027L;

    private TestCase testCaseUnderTest;

    private TestCaseResult testCaseResult;

    private String actualValue;

    private String resultMessage = StringUtils.EMPTY;

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

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public boolean isFailed() {
        return getTestCaseResult() == TestCaseResult.FAILED;
    }
}