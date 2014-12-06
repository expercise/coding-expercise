package com.kodility.domain.challenge;

import com.kodility.domain.AbstractEntity;
import com.kodility.domain.user.User;
import com.kodility.enums.DataType;
import com.kodility.enums.Lingo;
import com.kodility.utils.Clock;

import javax.persistence.*;
import java.util.*;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(nullable = false)
    private boolean approved;

    @Column(nullable = false)
    private int point = 1;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createDate = Clock.getTime();

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
            this.inputTypes.add(inputType);
        }
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

    public boolean hasLevel() {
        return level != null;
    }

    public Long getLevelId() {
        return level != null ? level.getId() : null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAuthor(User user) {
        return getUser().equals(user);
    }

    public boolean isNotAuthor(User user) {
        return !isAuthor(user);
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isNotApproved() {
        return !isApproved();
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
        Lingo lingo = Lingo.getLingo(lingoShortName).get();
        return descriptions.get(lingo);
    }

    public String getTitleFor(String lingoShortName) {
        Lingo lingo = Lingo.getLingo(lingoShortName).get();
        return titles.get(lingo);
    }

    public List<Object> getConvertedInputValues(List<TestCaseInputValue> inputValues) {
        List<Object> inputs = new ArrayList<>();
        for (int index = 0; index < inputTypes.size(); index++) {
            inputs.add(inputTypes.get(index).getInputType().convert(inputValues.get(index).getInputValue()));
        }
        return inputs;
    }

    public Object getOutputValueFor(TestCase testCase) {
        return outputType.convert(testCase.getOutput());
    }

}
