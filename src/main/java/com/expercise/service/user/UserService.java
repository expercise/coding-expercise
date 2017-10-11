package com.expercise.service.user;

import com.expercise.repository.challenge.SolutionRepository;
import com.expercise.repository.user.UserConnectionRepository;
import com.expercise.repository.user.UserRepository;
import com.expercise.repository.user.RememberMeTokenRepository;
import com.expercise.domain.user.RememberMeToken;
import com.expercise.domain.user.User;
import com.expercise.domain.user.UserConnection;
import com.expercise.service.notification.SlackMessage;
import com.expercise.service.notification.SlackNotificationService;
import com.expercise.service.util.UrlService;
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
    private UserRepository userRepository;

    @Autowired
    private UserConnectionRepository userConnectionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RememberMeTokenRepository rememberMeTokenRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private UrlService urlService;

    @Autowired
    private SlackNotificationService slackNotificationService;

    public void saveNewUser(User user) {
        String hashedUserPassword = hashPassword(user.getPassword());
        user.setPassword(hashedUserPassword);
        userRepository.save(user);

        sendNewUserNotification(user);
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
            userRepository.save(user);

            sendNewUserNotification(user);
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
        userConnection = userConnectionRepository.saveOrUpdate(userConnection);

        user.addUserConnection(userConnection);

        user = userRepository.saveOrUpdate(user);

        return user;
    }

    private void sendNewUserNotification(User user) {
        SlackMessage slackMessage = new SlackMessage();
        slackMessage.setChannel("#general");
        slackMessage.setText(
                String.format(
                        "A new user signed up: <%s|%s>.",
                        urlService.createUrlFor(user.getBookmarkableUrl()),
                        user.getFullName()
                ));
        slackNotificationService.sendMessage(slackMessage);
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
        UserConnection existingUserConnection = userConnectionRepository.findBy(connectionData.getProviderId(), connectionData.getProviderUserId());
        if (existingUserConnection != null) {
            User oldSocialUser = findById(NumberUtils.parseLong(existingUserConnection.getUserId()));
            oldSocialUser.getUserConnections().clear();
            userRepository.update(oldSocialUser);
        }

        return new UserConnection();
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(long id) {
        return userRepository.findOne(id);
    }

    public boolean emailNotRegisteredYet(String email) {
        return findByEmail(email) == null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<Long> findAllIds() {
        return userRepository.findAllIds();
    }

    @Transactional
    public void saveRememberMeToken(String email, String tokenValue, String series, Date lastUsedTime) {
        RememberMeToken token = new RememberMeToken();
        token.setEmail(email);
        token.setSeries(series);
        token.setToken(tokenValue);
        token.setLastUsedTime(lastUsedTime);
        rememberMeTokenRepository.update(token);
    }

    @Transactional
    public void updateRememberMeToken(String tokenValue, String series, Date lastUsedTime) {
        RememberMeToken token = rememberMeTokenRepository.findToken(series);
        token.setToken(tokenValue);
        token.setSeries(series);
        token.setLastUsedTime(lastUsedTime);
        rememberMeTokenRepository.update(token);
    }

    public RememberMeToken findRememberMeToken(String series) {
        return rememberMeTokenRepository.findToken(series);
    }

    public void removeRememberMeToken(String userId) {
        rememberMeTokenRepository.deleteToken(userId);
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean isNewbie() {
        if (authenticationService.isCurrentUserAuthenticated()) {
            return solutionRepository.countByUser(authenticationService.getCurrentUser()) == 0;
        }
        return false;
    }

}
