package com.expercise.repository.user;

import com.expercise.repository.BaseRepository;
import com.expercise.domain.user.UserConnection;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserConnectionRepository extends BaseRepository<UserConnection> {

    protected UserConnectionRepository() {
        super(UserConnection.class);
    }

    public UserConnection findBy(String providerId, String providerUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("providerId", providerId);
        params.put("providerUserId", providerUserId);
        return findOneBy(params);
    }

}
