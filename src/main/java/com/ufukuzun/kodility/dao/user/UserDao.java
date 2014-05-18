package com.ufukuzun.kodility.dao.user;

import com.ufukuzun.kodility.dao.AbstractHibernateDao;
import com.ufukuzun.kodility.domain.user.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao extends AbstractHibernateDao<User> {

    protected UserDao() {
        super(User.class);
    }

    @Transactional
    public void save(User user) {
        getCurrentSession().persist(user);
    }

    public User findByEmail(String email) {
        return findOneBy("email", email);
    }

}
