package com.expercise.service.level;

import com.expercise.domain.level.Level;
import com.expercise.domain.theme.Theme;
import com.expercise.domain.user.User;
import com.expercise.repository.level.LevelRepository;
import com.expercise.service.challenge.model.CurrentLevelModel;
import com.expercise.service.user.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private CurrentLevelHelper currentLevelHelper;

    @Autowired
    private AuthenticationService authenticationService;

    public List<Level> getAllLevelsInOrder() {
        return levelRepository.findAllByOrderByPriority();
    }

    public Level findById(Long id) {
        return levelRepository.findOne(id);
    }

    public CurrentLevelModel getCurrentLevelModelOfCurrentUserFor(Theme theme) {
        return getCurrentLevelModelOf(authenticationService.getCurrentUser(), theme);
    }

    public CurrentLevelModel getCurrentLevelModelOf(User user, Theme theme) {
        return currentLevelHelper.prepareCurrentLevelModelFor(user, theme.getOrderedLevels());
    }

    public void saveNewLevelOrUpdate(Level candidateLevel) {
        if (candidateLevel.isPersisted()) {
            Level originalLevel = mergeWithOriginalLevel(candidateLevel);
            levelRepository.save(originalLevel);
        } else {
            levelRepository.save(candidateLevel);
        }
    }

    private Level mergeWithOriginalLevel(Level candidateLevel) {
        Level originalLevel = findById(candidateLevel.getId());
        originalLevel.setNames(candidateLevel.getNames());
        originalLevel.setPriority(candidateLevel.getPriority());
        return originalLevel;
    }

    public void delete(Level level) {
        levelRepository.delete(level);
    }

    public boolean isValidToSave(Integer priority, Long levelId) {
        if (levelId != null) {
            Level level = findById(levelId);
            return level.getPriority() == priority;
        }
        return getByPriority(priority) == null;
    }

    public Level getByPriority(Integer priority) {
        return levelRepository.findByPriority(priority);
    }

}
