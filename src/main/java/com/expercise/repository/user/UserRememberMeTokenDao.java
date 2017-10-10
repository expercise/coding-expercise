package com.expercise.repository.user;

import com.expercise.repository.AbstractHibernateDao;
import com.expercise.domain.user.RememberMeToken;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRememberMeTokenDao extends AbstractHibernateDao<RememberMeToken> {

    protected UserRememberMeTokenDao() {
        super(RememberMeToken.class);
    }

    public RememberMeToken findToken(String series) {
        return findOneBy("series", series);
    }

    public void deleteToken(String email) {
        Query query = getCurrentSession().createQuery("delete from RememberMeToken where email = :email");
        query.setParameter("email", email);
        query.executeUpdate();
    }

}