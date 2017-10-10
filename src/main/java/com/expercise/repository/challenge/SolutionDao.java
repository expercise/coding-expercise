package com.expercise.repository.challenge;

import com.expercise.repository.AbstractHibernateDao;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.Solution;
import com.expercise.domain.level.Level;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
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

    public List<Solution> findAllSolutionsInLevelsOf(User user, List<Level> levels) {
        Criteria criteria = getCriteria();
        criteria.createAlias("challenge", "challenge");
        criteria.add(Restrictions.eq("user", user));
        criteria.add(Restrictions.eq("challenge.approved", true));
        criteria.add(Restrictions.in("challenge.level", levels));
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
        criteriaMap.put("challenge", challenge);
        return countBy(criteriaMap);
    }

    public long countByUser(User user) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("user", user));
        return countBy(criteria);
    }

}
