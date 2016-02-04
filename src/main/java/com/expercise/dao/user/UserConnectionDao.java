package com.expercise.dao.user;

import com.expercise.dao.AbstractHibernateDao;
import com.expercise.domain.user.UserConnection;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserConnectionDao extends AbstractHibernateDao<UserConnection> {

    protected UserConnectionDao() {
        super(UserConnection.class);
    }

    public UserConnection findBy(String providerId, String providerUserId) {
        return findOneBy(new HashMap<String, Object>() {{
            put("providerId", providerId);
            put("providerUserId", providerUserId);
        }});
    }

}
