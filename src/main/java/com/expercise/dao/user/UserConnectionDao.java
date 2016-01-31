package com.expercise.dao.user;

import com.expercise.dao.AbstractHibernateDao;
import com.expercise.domain.user.UserConnection;
import org.springframework.stereotype.Repository;

@Repository
public class UserConnectionDao extends AbstractHibernateDao<UserConnection> {

    protected UserConnectionDao() {
        super(UserConnection.class);
    }

}
