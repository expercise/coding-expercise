package com.kodility.service.challenge;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.Level;
import com.kodility.domain.challenge.Solution;
import com.kodility.domain.user.User;
import com.kodility.service.challenge.model.CurrentLevelModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ListIterator;

@Component
public class CurrentLevelHelper {

    @Autowired
    private LevelService levelService;

    @Autowired
    private SolutionService solutionService;

    public CurrentLevelModel prepareCurrentLevelModelFor(User user) {
        List<Level> levels = levelService.getAllLevelsInOrder();
        List<Solution> solutions = solutionService.getAllApprovedChallengeSolutionsOf(user);

        Level currentLevel = null;
        Level nextLevel = null;
        int challengeCountToNextLevel = 0;

        ListIterator<Level> levelIterator = levels.listIterator();
        while (levelIterator.hasNext()) {
            currentLevel = levelIterator.next();
            if (levelIterator.hasNext()) {
                nextLevel = levels.get(levelIterator.nextIndex());
            } else {
                nextLevel = null;
            }

            int countOfSolvedChallenges = countOfSolvedChallengesOfLevel(currentLevel, solutions);
            int countOfApprovedChallenges = currentLevel.getApprovedChallenges().size();
            if (countOfSolvedChallenges != countOfApprovedChallenges) {
                challengeCountToNextLevel = countOfApprovedChallenges - countOfSolvedChallenges;
                break;
            }
        }

        CurrentLevelModel currentLevelModel = new CurrentLevelModel();
        currentLevelModel.setCurrentLevel(currentLevel);
        currentLevelModel.setNextLevel(nextLevel);
        currentLevelModel.setChallengeCountToNextLevel(challengeCountToNextLevel);
        currentLevelModel.setSolutions(solutions);

        return currentLevelModel;
    }

    private int countOfSolvedChallengesOfLevel(Level level, List<Solution> solutions) {
        int count = 0;
        for (Challenge challenge : level.getApprovedChallenges()) {
            for (Solution eachSolution : solutions) {
                if (eachSolution.getChallenge().equals(challenge)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

}
