package com.ufukuzun.kodility.service.user;

import com.ufukuzun.kodility.dao.user.UserDao;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void saveNewUser(User user) {
        user.setRoles(new ArrayList<String>() {{
            add(UserRole.User.getRole());
        }});
        userDao.save(user);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public boolean emailNotRegisteredYet(String email) {
        return findByEmail(email) == null;
    }

}
