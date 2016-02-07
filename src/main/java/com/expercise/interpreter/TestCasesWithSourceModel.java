package com.expercise.interpreter;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestCasesWithSourceModel implements Serializable {

    private static final long serialVersionUID = 5558397147480824685L;

    private String currentSourceCode = StringUtils.EMPTY;

    private List<TestCaseModel> testCaseModels = new ArrayList<>();

    public static TestCasesWithSourceModel createFrom(TestCasesWithSourceCacheModel testCasesWithSourceCacheModel) {
        TestCasesWithSourceModel testCasesWithSourceModel = new TestCasesWithSourceModel();
        testCasesWithSourceModel.setCurrentSourceCode(testCasesWithSourceCacheModel.getCurrentSourceCode());
        for (TestCaseWithResult eachTestCaseWithResult : testCasesWithSourceCacheModel.getTestCaseResults()) {
            testCasesWithSourceModel.getTestCaseModels().add(TestCaseModel.createFrom(eachTestCaseWithResult));
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
