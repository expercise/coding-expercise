package com.expercise.domain.challenge;

import com.expercise.domain.BaseEntity;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "SEQ_USER_POINT")
public class UserPoint extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Challenge challenge;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgrammingLanguage programmingLanguage;

    @Column(nullable = false)
    private int pointAmount = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date givenDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public ProgrammingLanguage getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public int getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(int pointAmount) {
        this.pointAmount = pointAmount;
    }

    public Date getGivenDate() {
        return givenDate;
    }

    public void setGivenDate(Date givenDate) {
        this.givenDate = givenDate;
    }
}
