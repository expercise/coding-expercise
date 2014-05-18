package com.ufukuzun.kodility.service.user;

import com.ufukuzun.kodility.dao.user.UserDao;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public boolean emailNotRegisteredYet(String email) {
        return findByEmail(email) == null;
    }

}
