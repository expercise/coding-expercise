package com.expercise.repository.challenge;

import com.expercise.repository.AbstractHibernateDao;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.theme.Theme;
import com.expercise.domain.user.User;
import com.expercise.utils.collection.MapBuilder;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
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
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("user", user));
        criteria.addOrder(Order.desc("createDate"));
        return list(criteria);
    }

    @Override
    public List<Challenge> findAll() {
        Criteria criteria = getCriteria();
        criteria.addOrder(Order.desc("createDate"));
        return list(criteria);
    }

    public List<Challenge> findAllApprovedByUser(final User user) {
        return findAllBy(new MapBuilder<String, Object>().put("approved", true).put("user", user).build());
    }

    public Long countApprovedChallengesIn(Theme theme) {
        Criteria criteria = getCriteria();

        criteria.createAlias("level", "level");
        criteria.add(Restrictions.eq("level.theme", theme));
        criteria.add(Restrictions.eq("approved", true));

        return countBy(criteria);
    }

    public List<Challenge> findNotThemedApprovedChallenges() {
        Criteria criteria = getNotThemedApprovedChallengesCriteria();
        return list(criteria);
    }

    public Long countNotThemedApprovedChallenges() {
        Criteria criteria = getNotThemedApprovedChallengesCriteria();
        return countBy(criteria);
    }

    private Criteria getNotThemedApprovedChallengesCriteria() {
        return getCriteria()
                .createAlias("level", "level", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.disjunction(
                        Restrictions.isNull("level"),
                        Restrictions.isNull("level.theme")

                ))
                .add(Restrictions.eq("approved", true));
    }

}
