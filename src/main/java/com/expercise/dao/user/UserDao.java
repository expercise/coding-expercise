package com.expercise.dao.user;

import com.expercise.dao.AbstractHibernateDao;
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
