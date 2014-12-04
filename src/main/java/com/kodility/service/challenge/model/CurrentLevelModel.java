package com.kodility.service.challenge.model;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.Level;
import com.kodility.domain.challenge.Solution;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CurrentLevelModel {

    private Level currentLevel;

    private Level nextLevel;

    private int challengeCountToNextLevel;

    private List<Solution> solutions;

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Level getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(Level nextLevel) {
        this.nextLevel = nextLevel;
    }

    public int getChallengeCountToNextLevel() {
        return challengeCountToNextLevel;
    }

    public void setChallengeCountToNextLevel(int challengeCountToNextLevel) {
        this.challengeCountToNextLevel = challengeCountToNextLevel;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public boolean isLevelActive(Level levelToCompare) {
        return currentLevel != null && currentLevel.getPriority() >= levelToCompare.getPriority();
    }

    public boolean isLevelDeactive(Level levelToCompare) {
        return !isLevelActive(levelToCompare);
    }

    public boolean isSolved(Challenge challenge) {
        for (Solution solution : solutions) {
            if (solution.getChallenge().equals(challenge)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCurrentLevel(Level level) {
        return currentLevel != null && currentLevel.equals(level);
    }

    public int calculateProgressForCurrentLevel() {
        if (currentLevel == null) {
            return 0;
        }
        int totalChallengesCount = currentLevel.getApprovedChallenges().size();
        if (totalChallengesCount == 0) {
            return 0;
        }
        BigDecimal remainingChallengeCount = BigDecimal.valueOf(totalChallengesCount - challengeCountToNextLevel);
        BigDecimal rawProgress = remainingChallengeCount.divide(BigDecimal.valueOf(totalChallengesCount), 2, RoundingMode.CEILING);
        return rawProgress.multiply(BigDecimal.valueOf(100)).intValue();
    }

}
