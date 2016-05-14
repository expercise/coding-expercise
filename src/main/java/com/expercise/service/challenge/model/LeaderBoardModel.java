package com.expercise.service.challenge.model;

import com.expercise.domain.user.User;

import java.io.Serializable;

public class LeaderBoardModel implements Serializable {

    private User user;

    private Integer point;

    public LeaderBoardModel() {
    }

    public LeaderBoardModel(User user, Integer point) {
        this.user = user;
        this.point = point;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

}
