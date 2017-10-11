package com.expercise.domain.token;

import com.expercise.domain.BaseEntity;
import com.expercise.domain.user.User;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "SEQ_TOKEN")
public class Token extends BaseEntity {

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    @Column(nullable = false, length = 32)
    private String token;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

}
