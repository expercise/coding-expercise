package com.expercise.dao.challenge;

import com.expercise.dao.AbstractHibernateDao;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.user.User;
import com.expercise.utils.collection.MapBuilder;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
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

    public List<Challenge> findNotThemedApprovedChallenges() {
        Criteria criteria = getCriteria();

        criteria.createAlias("level", "level", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.disjunction(
                Restrictions.isNull("level"),
                Restrictions.isNull("level.theme")

        ));

        criteria.add(Restrictions.eq("approved", true));

        return list(criteria);
    }

}
