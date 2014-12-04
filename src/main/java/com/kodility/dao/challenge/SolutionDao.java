package com.kodility.dao.challenge;

import com.kodility.dao.AbstractHibernateDao;
import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.Solution;
import com.kodility.domain.user.User;
import com.kodility.enums.ProgrammingLanguage;
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
        addChallengeRestriction(criteriaMap, challenge);
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

    public List<Solution> findSolutionsBy(Challenge challenge, User user) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("challenge", challenge));
        criteria.add(Restrictions.eq("user", user));
        return criteria.list();
    }

    public long countByChallenge(Challenge challenge) {
        Map<String, Object> criteriaMap = new HashMap<>();
        addChallengeRestriction(criteriaMap, challenge);
        return countBy(criteriaMap);
    }

    private void addChallengeRestriction(Map<String, Object> criteriaMap, Challenge challenge) {
        criteriaMap.put("challenge", challenge);
    }
}
