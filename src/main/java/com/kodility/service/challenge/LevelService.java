package com.kodility.service.challenge;

import com.kodility.dao.challenge.LevelDao;
import com.kodility.domain.challenge.Level;
import com.kodility.domain.user.User;
import com.kodility.service.challenge.model.CurrentLevelModel;
import com.kodility.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelService {

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private CurrentLevelHelper currentLevelHelper;

    @Autowired
    private AuthenticationService authenticationService;

    public List<Level> getAllLevelsInOrder() {
        return levelDao.findAllOrdered();
    }

    public Level findById(Long id) {
        return levelDao.findOne(id);
    }

    public CurrentLevelModel getCurrentLevelModelOfCurrentUser() {
        return getCurrentLevelModelOf(authenticationService.getCurrentUser());
    }

    public CurrentLevelModel getCurrentLevelModelOf(User user) {
        return currentLevelHelper.prepareCurrentLevelModelFor(user);
    }

    public void save(Level level) {
        levelDao.save(level);
    }

    public Level getByPriority(Integer priority) {
        return levelDao.findOneBy(priority);
    }

}
