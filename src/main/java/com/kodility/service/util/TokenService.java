package com.kodility.service.util;

import com.kodility.dao.user.TokenDao;
import com.kodility.domain.token.Token;
import com.kodility.domain.token.TokenType;
import com.kodility.domain.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenDao tokenDao;

    public String createTokenFor(User user, TokenType tokenType) {
        deletedOldTokenOf(user);
        Token token = new Token();
        token.setUser(user);
        token.setTokenType(tokenType);
        token.setToken(generateUniqueToken());
        tokenDao.save(token);
        return token.getToken();
    }

    private void deletedOldTokenOf(User user) {
        Token unusedToken = tokenDao.findOneBy("user", user);
        if (unusedToken != null) {
            tokenDao.delete(unusedToken);
        }
    }

    private String generateUniqueToken() {
        String generatedToken;
        Token foundToken;
        do {
            generatedToken = RandomStringUtils.randomAlphabetic(32);
            foundToken = tokenDao.findOneBy("token", generatedToken);
        }
        while (foundToken != null);
        return generatedToken;
    }

}
