package com.expercise.service.util;

import com.expercise.repository.user.TokenRepository;
import com.expercise.domain.token.Token;
import com.expercise.domain.token.TokenType;
import com.expercise.domain.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public static final int TOKEN_LENGTH = 32;

    @Autowired
    private TokenRepository tokenRepository;

    public String createTokenFor(User user, TokenType tokenType) {
        deletedOldTokenOf(user);
        Token token = new Token();
        token.setUser(user);
        token.setTokenType(tokenType);
        token.setToken(generateUniqueTokenFor(tokenType));
        tokenRepository.save(token);
        return token.getToken();
    }

    private void deletedOldTokenOf(User user) {
        Token unusedToken = tokenRepository.findOneBy("user", user);
        if (unusedToken != null) {
            tokenRepository.delete(unusedToken);
        }
    }

    private String generateUniqueTokenFor(TokenType tokenType) {
        String generatedToken;
        Token foundToken;
        do {
            generatedToken = RandomStringUtils.randomAlphabetic(TOKEN_LENGTH);
            foundToken = findBy(generatedToken, tokenType);
        }
        while (foundToken != null);
        return generatedToken;
    }

    public Token findBy(String token, TokenType tokenType) {
        return tokenRepository.findToken(token, tokenType);
    }

    public void deleteToken(String token, TokenType tokenType) {
        Token foundToken = findBy(token, tokenType);
        if (foundToken != null) {
            tokenRepository.delete(foundToken);
        }
    }
}
