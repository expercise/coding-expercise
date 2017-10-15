package com.expercise.service.user;

import com.expercise.domain.user.RememberMeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public class PersistentRememberMeTokenRepositoryService implements PersistentTokenRepository {

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        userService.saveRememberMeToken(token.getUsername(), token.getTokenValue(), token.getSeries(), token.getDate());
    }

    @Transactional
    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        userService.updateRememberMeToken(tokenValue, series, lastUsed);
    }

    @Transactional
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        RememberMeToken rememberMeToken = userService.findRememberMeToken(seriesId);
        if (rememberMeToken == null) {
            return null;
        }
        return new PersistentRememberMeToken(rememberMeToken.getEmail(), seriesId, rememberMeToken.getToken(), rememberMeToken.getLastUsedTime());
    }

    @Transactional
    @Override
    public void removeUserTokens(String userId) {
        userService.removeRememberMeToken(userId);
    }

}
