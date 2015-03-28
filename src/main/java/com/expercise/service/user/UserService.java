package com.expercise.service.user;

import com.expercise.dao.user.UserDao;
import com.expercise.domain.user.User;
import com.expercise.utils.PasswordEncoder;
import com.expercise.dao.user.UserRememberMeTokenDao;
import com.expercise.domain.user.RememberMeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRememberMeTokenDao userRememberMeTokenDao;

    public void saveNewUser(User user) {
        String hashedUserPassword = hashPassword(user.getPassword());
        user.setPassword(hashedUserPassword);
        userDao.save(user);
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
    public void saveRememberMeToken(String email, String tokenValue, String series, Date lastUsedTime){
        RememberMeToken token = new RememberMeToken();
        token.setEmail(email);
        token.setSeries(series);
        token.setToken(tokenValue);
        token.setLastUsedTime(lastUsedTime);
        userRememberMeTokenDao.update(token);
    }

    @Transactional
    public void updateRememberMeToken(String tokenValue, String series, Date lastUsedTime){
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

}
