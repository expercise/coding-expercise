package com.ufukuzun.kodility.dao.challenge;

import com.ufukuzun.kodility.dao.AbstractHibernateDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.UserPoint;
import com.ufukuzun.kodility.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserPointDao extends AbstractHibernateDao<UserPoint> {

    protected UserPointDao() {
        super(UserPoint.class);
    }

    public UserPoint findByChallengeAndUser(Challenge challenge, User user) {
        HashMap<String, Object> criteriaParams = new HashMap<>();
        criteriaParams.put("challenge", challenge);
        criteriaParams.put("user", user);

        return findOneBy(criteriaParams);
    }

    public long countByChallengeAndUser(Challenge challenge, User user) {
        HashMap<String, Object> criteriaParams = new HashMap<>();
        criteriaParams.put("challenge", challenge);
        criteriaParams.put("user", user);

        return countBy(criteriaParams);
    }

}
