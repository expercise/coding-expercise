package com.expercise.repository.challenge;

import com.expercise.repository.BaseRepository;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.UserPoint;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserPointRepository extends BaseRepository<UserPoint> {

    protected UserPointRepository() {
        super(UserPoint.class);
    }

    public UserPoint findByChallengeAndUser(Challenge challenge, User user) {
        Map<String, Object> criteriaParams = new HashMap<>();
        addChallengeRestriction(criteriaParams, challenge);
        addUserRestriction(criteriaParams, user);

        return findOneBy(criteriaParams);
    }

    public long countForPointGivingCriteria(Challenge challenge, User user, ProgrammingLanguage programmingLanguage) {
        Map<String, Object> criteriaParams = new HashMap<>();
        addChallengeRestriction(criteriaParams, challenge);
        addUserRestriction(criteriaParams, user);
        criteriaParams.put("programmingLanguage", programmingLanguage);

        return countBy(criteriaParams);
    }

    public long getTotalPointsOf(Long userId) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("user.id", userId));
        return sumBy("pointAmount", criteria);
    }

    private void addUserRestriction(Map<String, Object> criteriaParams, User user) {
        criteriaParams.put("user", user);
    }

    private void addChallengeRestriction(Map<String, Object> criteriaParams, Challenge challenge) {
        criteriaParams.put("challenge", challenge);
    }

}
