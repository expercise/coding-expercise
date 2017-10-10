package com.expercise.repository.user;

import com.expercise.repository.AbstractHibernateDao;
import com.expercise.domain.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractHibernateDao<User> {

    protected UserDao() {
        super(User.class);
    }

    public User findByEmail(String email) {
        return findOneBy("email", email);
    }

}
