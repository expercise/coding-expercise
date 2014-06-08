package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.UserPointDao;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.UserPoint;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.service.configuration.ConfigurationService;
import com.ufukuzun.kodility.utils.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPointService {

    @Autowired
    private UserPointDao userPointDao;

    @Autowired
    private ConfigurationService configurationService;

    @Transactional
    public void givePoint(Challenge challenge, User user) {
        UserPoint userPoint = new UserPoint();
        userPoint.setChallenge(challenge);
        userPoint.setUser(user);
        userPoint.setPointAmount(configurationService.getValueAsInteger("challenge.defaultpointamount"));
        userPoint.setGivenDate(Clock.getTime());
        userPointDao.save(userPoint);
    }

    public boolean canUserWinPoint(Challenge challenge, User user) {
        UserPoint foundPoint = userPointDao.findByChallengeAndUser(challenge, user);
        if (foundPoint == null) {
            return true;
        }
        return false;
    }

}
