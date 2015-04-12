package com.expercise.interpreter;

import com.expercise.domain.challenge.TestCase;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestCasesWithSourceModel implements Serializable {

    private String currentSourceCode = StringUtils.EMPTY;

    private List<TestCaseModel> testCaseModels = new ArrayList<>();

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

    public static TestCasesWithSourceModel createFrom(TestCasesWithSourceCacheModel testCasesWithSourceCacheModel) {
        TestCasesWithSourceModel testCasesWithSourceModel = new TestCasesWithSourceModel();
        testCasesWithSourceModel.setCurrentSourceCode(testCasesWithSourceCacheModel.getCurrentSourceCode());
        for (TestCaseWithResult eachTestCaseWithResult : testCasesWithSourceCacheModel.getTestCaseResults()) {
            testCasesWithSourceModel.getTestCaseModels().add(createFrom(eachTestCaseWithResult));
        }
        return testCasesWithSourceModel;
    }

    public String getCurrentSourceCode() {
        return currentSourceCode;
    }

    public void setCurrentSourceCode(String currentSourceCode) {
        this.currentSourceCode = currentSourceCode;
    }

    public List<TestCaseModel> getTestCaseModels() {
        return testCaseModels;
    }

    public void setTestCaseModels(List<TestCaseModel> testCaseModels) {
        this.testCaseModels = testCaseModels;
    }

}
