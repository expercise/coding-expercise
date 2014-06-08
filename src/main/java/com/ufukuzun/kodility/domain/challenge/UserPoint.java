package com.ufukuzun.kodility.domain.challenge;

import com.ufukuzun.kodility.domain.AbstractEntity;
import com.ufukuzun.kodility.domain.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserPoint extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Challenge challenge;

    @Column(nullable = false)
    private int pointAmount = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date givenDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
