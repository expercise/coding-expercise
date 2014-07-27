package com.ufukuzun.kodility.dao.challenge;

import com.ufukuzun.kodility.dao.AbstractHibernateDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.utils.collection.MapBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChallengeDao extends AbstractHibernateDao<Challenge> {

    public ChallengeDao() {
        super(Challenge.class);
    }

    public List<Challenge> findAllApproved() {
        return findAllBy(new MapBuilder<String, Object>().put("approved", true).build());
    }

    public List<Challenge> findAllByUser(final User user) {
        return findAllBy(new MapBuilder<String, Object>().put("user", user).build());
    }

}
