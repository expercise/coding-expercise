package com.expercise.domain.challenge;

import com.expercise.domain.AbstractEntity;
import com.expercise.domain.level.Level;
import com.expercise.domain.theme.Theme;
import com.expercise.domain.user.User;
import com.expercise.enums.DataType;
import com.expercise.enums.Lingo;
import com.expercise.utils.Clock;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.*;

@Entity
public class Challenge extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChallengeType challengeType = ChallengeType.ALGORITHM;

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "Lingo", nullable = false)
    @Column(name = "Title", nullable = false)
    private Map<Lingo, String> titles = new HashMap<>();

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "Lingo", nullable = false)
    @Column(name = "Description", nullable = false, length = 2048)
    private Map<Lingo, String> descriptions = new HashMap<>();

    @OneToMany(mappedBy = "challenge", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("priority")
    private List<ChallengeInputType> inputTypes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DataType outputType;

    @OneToMany(mappedBy = "challenge", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("priority")
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

    public boolean hasNotLevel() {
        return !hasLevel();
    }

    public String getThemeBookmarkableUrl() {
        if (hasLevel() && level.hasTheme()) {
            return level.getTheme().getBookmarkableUrl();
        }
        return Theme.URL_FOR_NOT_THEMED_CHALLENGES;
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

    public ChallengeType getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(ChallengeType challengeType) {
        this.challengeType = challengeType;
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

    public String getDescription() {
        return Lingo.getValueWithLingoSafe(descriptions);
    }

    public boolean hasDescriptionForCurrentLocale() {
        return StringUtils.isNotBlank(Lingo.getValueFrom(descriptions));
    }

    public String getTitle() {
        return Lingo.getValueWithLingoSafe(titles);
    }

    public boolean hasTitleForCurrentLocale() {
        return StringUtils.isNotBlank(Lingo.getValueFrom(titles));
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

    public boolean isCodeKata() {
        return getChallengeType() == ChallengeType.CODE_KATA;
    }

}
