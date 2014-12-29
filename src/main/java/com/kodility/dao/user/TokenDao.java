package com.kodility.dao.user;

import com.kodility.dao.AbstractHibernateDao;
import com.kodility.domain.token.Token;
import org.springframework.stereotype.Repository;

@Repository
public class TokenDao extends AbstractHibernateDao<Token> {

    protected TokenDao() {
        super(Token.class);
    }

}
