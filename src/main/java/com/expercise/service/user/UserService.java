package com.expercise.service.user;

import com.expercise.dao.challenge.SolutionDao;
import com.expercise.dao.user.UserConnectionDao;
import com.expercise.dao.user.UserDao;
import com.expercise.dao.user.UserRememberMeTokenDao;
import com.expercise.domain.user.RememberMeToken;
import com.expercise.domain.user.User;
import com.expercise.domain.user.UserConnection;
import com.expercise.utils.NumberUtils;
import com.expercise.utils.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserConnectionDao userConnectionDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRememberMeTokenDao userRememberMeTokenDao;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SolutionDao solutionDao;

    public void saveNewUser(User user) {
        String hashedUserPassword = hashPassword(user.getPassword());
        user.setPassword(hashedUserPassword);
        userDao.save(user);
    }

    @Transactional
    public User saveSocialUser(UserProfile userProfile, ConnectionData connectionData) {
        String email = userProfile.getEmail();
        User user = initializeSocialUser(email);

        String imageUrl = SocialUserDetailsHelper.getImageUrl(connectionData);
        if (user.isNotPersisted()) {
            user.setEmail(email);
            user.setFirstName(SocialUserDetailsHelper.getFirstName(userProfile));
            user.setLastName(userProfile.getLastName());
            user.setPassword(UUID.randomUUID().toString());
            user.setSocialImageUrl(imageUrl);
            userDao.save(user);
        } else {
            user.setAvatar(null);
            user.setSocialImageUrl(imageUrl);
        }

        UserConnection userConnection = initializeUserConnection(connectionData);
        userConnection.setUserId(user.getId().toString());
        userConnection.setAccessToken(connectionData.getAccessToken());
        userConnection.setDisplayName(connectionData.getDisplayName());
        userConnection.setExpireTime(connectionData.getExpireTime());
        userConnection.setImageUrl(imageUrl);
        userConnection.setProfileUrl(connectionData.getProfileUrl());
        userConnection.setProviderId(connectionData.getProviderId());
        userConnection.setProviderUserId(connectionData.getProviderUserId());
        userConnection.setRefreshToken(connectionData.getRefreshToken());
        userConnection.setSecret(connectionData.getSecret());
        userConnection.setRank(0);
        userConnection = userConnectionDao.saveOrUpdate(userConnection);

        user.addUserConnection(userConnection);

        user = userDao.saveOrUpdate(user);

        return user;
    }

    private User initializeSocialUser(String email) {
        if (authenticationService.isCurrentUserAuthenticated()) {
            return authenticationService.getCurrentUser();
        }

        User user = new User();
        if (StringUtils.isNotBlank(email)) {
            User existingUser = findByEmail(email);
            if (existingUser != null) {
                user = existingUser;
            }
        }
        return user;
    }

    private UserConnection initializeUserConnection(ConnectionData connectionData) {
        UserConnection existingUserConnection = userConnectionDao.findBy(connectionData.getProviderId(), connectionData.getProviderUserId());
        if (existingUserConnection != null) {
            User oldSocialUser = findById(NumberUtils.parseLong(existingUserConnection.getUserId()));
            oldSocialUser.getUserConnections().clear();
            userDao.update(oldSocialUser);
        }

        return new UserConnection();
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public User findById(long id) {
        return userDao.findOne(id);
    }

    public boolean emailNotRegisteredYet(String email) {
        return findByEmail(email) == null;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    public void saveRememberMeToken(String email, String tokenValue, String series, Date lastUsedTime) {
        RememberMeToken token = new RememberMeToken();
        token.setEmail(email);
        token.setSeries(series);
        token.setToken(tokenValue);
        token.setLastUsedTime(lastUsedTime);
        userRememberMeTokenDao.update(token);
    }

    @Transactional
    public void updateRememberMeToken(String tokenValue, String series, Date lastUsedTime) {
        RememberMeToken token = userRememberMeTokenDao.findToken(series);
        token.setToken(tokenValue);
        token.setSeries(series);
        token.setLastUsedTime(lastUsedTime);
        userRememberMeTokenDao.update(token);
    }

    public RememberMeToken findRememberMeToken(String series) {
        return userRememberMeTokenDao.findToken(series);
    }

    public void removeRememberMeToken(String userId) {
        userRememberMeTokenDao.deleteToken(userId);
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean isNewbie() {
        if (authenticationService.isCurrentUserAuthenticated()) {
            return solutionDao.countByUser(authenticationService.getCurrentUser()) == 0;
        }
        return true;
    }

}
