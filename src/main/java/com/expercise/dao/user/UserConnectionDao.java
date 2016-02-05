package com.expercise.dao.user;

import com.expercise.dao.AbstractHibernateDao;
import com.expercise.domain.user.UserConnection;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserConnectionDao extends AbstractHibernateDao<UserConnection> {

    protected UserConnectionDao() {
        super(UserConnection.class);
    }

    public UserConnection findBy(String providerId, String providerUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("providerId", providerId);
        params.put("providerUserId", providerUserId);
        return findOneBy(params);
    }

}
