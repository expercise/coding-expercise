package com.expercise.repository.user;

import com.expercise.domain.token.Token;
import com.expercise.domain.token.TokenType;
import com.expercise.domain.user.User;
import com.expercise.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface TokenRepository extends BaseRepository<Token> {

    Token findByTokenAndTokenType(String token, TokenType tokenType);

    @Modifying
    void deleteByUser(User user);

}
