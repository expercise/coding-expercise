package com.ufukuzun.kodility.dao.challenge;

import com.ufukuzun.kodility.dao.AbstractHibernateDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import org.springframework.stereotype.Repository;

@Repository
public class ChallengeDao extends AbstractHibernateDao<Challenge> {

    public ChallengeDao() {
        super(Challenge.class);
    }



}
