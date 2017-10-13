package com.expercise.repository.user;

import com.expercise.domain.user.UserConnection;
import com.expercise.repository.BaseRepository;

public interface UserConnectionRepository extends BaseRepository<UserConnection> {

    UserConnection findByProviderIdAndProviderUserId(String providerId, String providerUserId);

}
