package com.kodility.service.challenge;

import com.kodility.dao.challenge.UserPointDao;
import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.UserPoint;
import com.kodility.domain.user.User;
import com.kodility.enums.ProgrammingLanguage;
import com.kodility.utils.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPointService {

    @Autowired
    private UserPointDao userPointDao;

    @Transactional
    public void givePoint(Challenge challenge, User user, ProgrammingLanguage programmingLanguage) {
        UserPoint userPoint = new UserPoint();
        userPoint.setChallenge(challenge);
        userPoint.setUser(user);
        userPoint.setProgrammingLanguage(programmingLanguage);
        userPoint.setPointAmount(challenge.getPoint());
        userPoint.setGivenDate(Clock.getTime());

        userPointDao.save(userPoint);
    }

    public boolean canUserWinPoint(Challenge challenge, User user, ProgrammingLanguage programmingLanguage) {
        return !challenge.isNotApproved() && userPointDao.countForPointGivingCriteria(challenge, user, programmingLanguage) == 0L;
    }

    public long getTotalPointsOf(User user) {
        return userPointDao.getTotalPointsOf(user);
    }

}
