package com.kodility.domain.token;

import com.kodility.domain.AbstractEntity;
import com.kodility.domain.user.User;

import javax.persistence.*;

@Entity
public class Token extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    @Column(nullable = false, length = 32)
    private String token;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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
