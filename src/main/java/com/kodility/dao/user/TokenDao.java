package com.kodility.dao.user;

import com.kodility.dao.AbstractHibernateDao;
import com.kodility.domain.token.Token;
import com.kodility.domain.token.TokenType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TokenDao extends AbstractHibernateDao<Token> {

    protected TokenDao() {
        super(Token.class);
    }

    public Token findToken(String token, TokenType tokenType) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("tokenType", tokenType);
        return findOneBy(params);
    }

}
