package com.expercise.service.user;

import com.expercise.dao.user.UserDao;
import com.expercise.domain.user.User;
import com.expercise.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        String hashedUserPassword = hashPassword(user.getPassword());
        user.setPassword(hashedUserPassword);
        userDao.save(user);
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

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
