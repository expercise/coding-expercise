package com.expercise.interpreter;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestCasesWithSourceModel implements Serializable {

    private static final long serialVersionUID = 5558397147480824685L;

    private String currentSourceCode = StringUtils.EMPTY;

    private List<TestCaseModel> testCaseModels = new ArrayList<>();

    public TestCasesWithSourceModel() {
    }

    public TestCasesWithSourceModel(TestCasesWithSourceCacheModel testCasesWithSourceCacheModel) {
        currentSourceCode = testCasesWithSourceCacheModel.getCurrentSourceCode();
        testCaseModels = testCasesWithSourceCacheModel.getTestCaseResults()
                .stream()
                .map(TestCaseModel::new)
                .collect(Collectors.toList());
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

}
