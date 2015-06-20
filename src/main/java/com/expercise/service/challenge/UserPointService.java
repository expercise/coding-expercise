package com.expercise.service.challenge;

import com.expercise.dao.challenge.UserPointDao;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.UserPoint;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.utils.Clock;
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
        if (challenge.getUser().equals(user)) {
            return false;
        }
        return challenge.isApproved() && userPointDao.countForPointGivingCriteria(challenge, user, programmingLanguage) == 0L;
    }

    public long getTotalPointsOf(User user) {
        return userPointDao.getTotalPointsOf(user);
    }

}
