package com.expercise.service.challenge.model;

import com.expercise.domain.user.User;

import java.io.Serializable;

public class LeaderBoardModel implements Serializable {

    private User user;

    private Double point;

    public LeaderBoardModel() {
    }

    public LeaderBoardModel(User user, Double point) {
        this.user = user;
        this.point = point;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

}
