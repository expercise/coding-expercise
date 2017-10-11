package com.expercise.domain.user;

import com.expercise.domain.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "SEQ_REMEMBER_ME_TOKEN")
public class RememberMeToken extends BaseEntity {

    @Column(nullable = false)
    private String email;

    @Column(length = 64, unique = true, nullable = false)
    private String series;

    @Column(length = 64, nullable = false)
    private String token;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsedTime;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(Date lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

}
