package com.ufukuzun.kodility.dao.challenge;

import com.ufukuzun.kodility.dao.AbstractHibernateDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ChallengeDao extends AbstractHibernateDao<Challenge> {

    public ChallengeDao() {
        super(Challenge.class);
    }

    public List<Challenge> findAllApproved() {
        return findAllBy(new HashMap<String, Object>() {{
            put("approved", true);
        }});
    }

}
