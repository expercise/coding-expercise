package com.ufukuzun.kodility.testutils.builder;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.UserPoint;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;

import java.util.Date;

public class UserPointBuilder extends AbstractEntityBuilder<UserPoint, UserPointBuilder> {

    private User user;

    private Challenge challenge;

    private ProgrammingLanguage programmingLanguage;

    private int pointAmount;

    private Date givenDate;

    @Override
    protected UserPoint doBuild() {
        UserPoint userPoint = new UserPoint();
        userPoint.setUser(user);
        userPoint.setChallenge(challenge);
        userPoint.setProgrammingLanguage(programmingLanguage);
        userPoint.setPointAmount(pointAmount);
        userPoint.setGivenDate(givenDate);
        return userPoint;
    }

    public UserPointBuilder user(User user) {
        this.user = user;
        return this;
    }

    public UserPointBuilder challenge(Challenge challenge) {
        this.challenge = challenge;
        return this;
    }

    public UserPointBuilder programmingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
        return this;
    }

    public UserPointBuilder pointAmount(int pointAmount) {
        this.pointAmount = pointAmount;
        return this;
    }

    public UserPointBuilder givenDate(Date givenDate) {
        this.givenDate = givenDate;
        return this;
    }

}
