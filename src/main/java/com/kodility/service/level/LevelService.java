package com.kodility.service.level;

import com.kodility.dao.level.LevelDao;
import com.kodility.domain.level.Level;
import com.kodility.domain.theme.Theme;
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
        return levelDao.findAllOrderedByPriority();
    }

    public Level findById(Long id) {
        return levelDao.findOne(id);
    }

    public CurrentLevelModel getCurrentLevelModelOfCurrentUserFor(Theme theme) {
        return getCurrentLevelModelOf(authenticationService.getCurrentUser(), theme);
    }

    public CurrentLevelModel getCurrentLevelModelOf(User user, Theme theme) {
        return currentLevelHelper.prepareCurrentLevelModelFor(user, theme.getOrderedLevels());
    }

    public void save(Level level) {
        levelDao.save(level);
    }

    public void delete(Level level) {
        levelDao.delete(level);
    }

    public Level getByPriority(Integer priority) {
        return levelDao.findOneBy(priority);
    }

}
