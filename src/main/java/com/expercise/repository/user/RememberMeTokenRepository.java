package com.expercise.repository.user;

import com.expercise.domain.user.RememberMeToken;
import com.expercise.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface RememberMeTokenRepository extends BaseRepository<RememberMeToken> {

    RememberMeToken findBySeries(String series);

    @Modifying
    void deleteByEmail(String email);

}