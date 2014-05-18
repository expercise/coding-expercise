package com.ufukuzun.kodility.domain.challenge;

import com.ufukuzun.kodility.domain.AbstractEntity;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.enums.Lingo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Challenge extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "Lingo", nullable = false)
    @Column(name = "Title", nullable = false)
    private Map<Lingo, String> titles = new HashMap<>();

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "Lingo", nullable = false)
    @Column(name = "Description", nullable = false)
    private Map<Lingo, String> descriptions = new HashMap<>();

    @OneToMany(mappedBy = "challenge", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("priority")
    private List<ChallengeInputType> inputTypes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private DataType outputType;

    @OneToMany(mappedBy = "challenge", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<TestCase> testCases = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Level level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Lingo, String> getTitles() {
        return titles;
    }

    public void setTitles(Map<Lingo, String> titles) {
        this.titles = titles;
    }

    public Map<Lingo, String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Map<Lingo, String> descriptions) {
        this.descriptions = descriptions;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    public List<ChallengeInputType> getInputTypes() {
        return inputTypes;
    }

    public void setInputTypes(List<ChallengeInputType> inputTypes) {
        for (ChallengeInputType inputType : inputTypes) {
            inputType.setChallenge(this);
        }
        this.inputTypes = inputTypes;
    }

    public DataType getOutputType() {
        return outputType;
    }

    public void setOutputType(DataType outputType) {
        this.outputType = outputType;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void addTestCase(TestCase testCase) {
        testCase.setChallenge(this);
        testCases.add(testCase);
    }

    public void addTestCase(List<String> inputs, String output) {
        TestCase testCase = new TestCase();
        testCase.setInputs(TestCaseInputValue.createFrom(inputs));
        testCase.setOutput(output);
        addTestCase(testCase);
    }

    public String getDescriptionFor(String lingoShortName) {
        Lingo lingo = Lingo.getLingo(lingoShortName);
        return descriptions.get(lingo);
    }

    public String getTitleFor(String lingoShortName) {
        Lingo lingo = Lingo.getLingo(lingoShortName);
        return titles.get(lingo);
    }

    public List<Object> getConvertedInputValues(List<TestCaseInputValue> inputValues) {
        List<Object> inputs = new ArrayList<>();
        for (int index = 0; index < inputTypes.size(); index++) {
            inputs.add(inputTypes.get(index).getInputType().convert(inputValues.get(index).getInputValue()));
        }
        return inputs;
    }

}
