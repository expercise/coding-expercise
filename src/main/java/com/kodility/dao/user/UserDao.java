package com.kodility.dao.user;

import com.kodility.dao.AbstractHibernateDao;
import com.kodility.domain.user.User;
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
