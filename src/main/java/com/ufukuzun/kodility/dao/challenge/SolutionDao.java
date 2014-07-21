package com.ufukuzun.kodility.dao.challenge;

import com.ufukuzun.kodility.dao.AbstractHibernateDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SolutionDao extends AbstractHibernateDao<Solution> {

    protected SolutionDao() {
        super(Solution.class);
    }

    public Solution findBy(Challenge challenge, User user, ProgrammingLanguage programmingLanguage) {
        Map<String, Object> criteriaMap = new HashMap<>();
        criteriaMap.put("challenge", challenge);
        criteriaMap.put("user", user);
        criteriaMap.put("programmingLanguage", programmingLanguage);
        return findOneBy(criteriaMap);
    }

    public List<Solution> findApprovedChallengeSolutionsByUser(User user) {
        Criteria criteria = getCriteria();
        criteria.createAlias("challenge", "challenge");
        criteria.add(Restrictions.eq("user", user));
        criteria.add(Restrictions.eq("challenge.approved", true));
        return criteria.list();
    }

    public long countByChallenge(Challenge challenge) {
        Map<String, Object> criteriaMap = new HashMap<>();
        criteriaMap.put("challenge", challenge);
        return countBy(criteriaMap);
    }

}
