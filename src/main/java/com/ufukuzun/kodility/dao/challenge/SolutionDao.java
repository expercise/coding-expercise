package com.ufukuzun.kodility.dao.challenge;

import com.ufukuzun.kodility.dao.AbstractHibernateDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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

}
