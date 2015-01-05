package com.kodility.service.level;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.Solution;
import com.kodility.domain.level.Level;
import com.kodility.domain.user.User;
import com.kodility.service.challenge.SolutionService;
import com.kodility.service.challenge.model.CurrentLevelModel;
import com.kodility.testutils.builder.ChallengeBuilder;
import com.kodility.testutils.builder.LevelBuilder;
import com.kodility.testutils.builder.SolutionBuilder;
import com.kodility.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.kodility.testutils.asserts.Asserts.assertExpectedItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrentLevelHelperTest {

    @InjectMocks
    private CurrentLevelHelper helper;

    @Mock
    private SolutionService solutionService;

    @Test
    public void shouldPrepareCurrentLevelModelForUserWhenUserSolvedAllChallengesOfPreviousLevels() {
        Level level1 = new LevelBuilder().priority(1).buildWithRandomId();
        Level level2 = new LevelBuilder().priority(2).buildWithRandomId();
        Level level3 = new LevelBuilder().priority(3).buildWithRandomId();

        Challenge challenge1 = new ChallengeBuilder().level(level1).approved(true).buildWithRandomId();
        Challenge challenge2 = new ChallengeBuilder().level(level1).approved(true).buildWithRandomId();
        Challenge challenge3 = new ChallengeBuilder().level(level2).approved(true).buildWithRandomId();
        Challenge challenge4 = new ChallengeBuilder().level(level2).approved(true).buildWithRandomId();
        Challenge challenge5 = new ChallengeBuilder().level(level2).approved(true).buildWithRandomId();
        Challenge challenge6 = new ChallengeBuilder().level(level3).approved(true).buildWithRandomId();

        Solution solution1 = new SolutionBuilder().challenge(challenge1).buildWithRandomId();
        Solution solution2 = new SolutionBuilder().challenge(challenge2).buildWithRandomId();

        User user = new UserBuilder().buildWithRandomId();

        List<Level> levels = Arrays.asList(level1, level2, level3);
        when(solutionService.getAllSolutionsInLevelsOf(user, levels)).thenReturn(Arrays.asList(solution1, solution2));

        CurrentLevelModel currentLevelModel = helper.prepareCurrentLevelModelFor(user, levels);

        assertThat(currentLevelModel.getCurrentLevel(), equalTo(level2));
        assertThat(currentLevelModel.getNextLevel(), equalTo(level3));
        assertThat(currentLevelModel.getChallengeCountToNextLevel(), equalTo(3));
        assertExpectedItems(currentLevelModel.getSolutions(), solution1, solution2);
    }

    @Test
    public void shouldPrepareCurrentLevelModelForUserWhenUserSolvedSomeChallengesOfPreviousLevels() {
        Level level1 = new LevelBuilder().priority(1).buildWithRandomId();
        Level level2 = new LevelBuilder().priority(2).buildWithRandomId();

        Challenge challenge1 = new ChallengeBuilder().level(level1).approved(true).buildWithRandomId();
        Challenge challenge2 = new ChallengeBuilder().level(level1).approved(true).buildWithRandomId();
        Challenge challenge3 = new ChallengeBuilder().level(level2).approved(true).buildWithRandomId();
        Challenge challenge4 = new ChallengeBuilder().level(level2).approved(true).buildWithRandomId();
        Challenge challenge5 = new ChallengeBuilder().level(level2).approved(true).buildWithRandomId();

        Solution solution1 = new SolutionBuilder().challenge(challenge1).buildWithRandomId();
        Solution solution2 = new SolutionBuilder().challenge(challenge2).buildWithRandomId();
        Solution solution3 = new SolutionBuilder().challenge(challenge4).buildWithRandomId();

        User user = new UserBuilder().buildWithRandomId();

        List<Level> levels = Arrays.asList(level1, level2);
        when(solutionService.getAllSolutionsInLevelsOf(user, levels)).thenReturn(Arrays.asList(solution1, solution2, solution3));

        CurrentLevelModel currentLevelModel = helper.prepareCurrentLevelModelFor(user, levels);

        assertThat(currentLevelModel.getCurrentLevel(), equalTo(level2));
        assertThat(currentLevelModel.getNextLevel(), nullValue());
        assertThat(currentLevelModel.getChallengeCountToNextLevel(), equalTo(2));
        assertExpectedItems(currentLevelModel.getSolutions(), solution1, solution2, solution3);
    }

    @Test
    public void shouldNotCountNotApprovedChallenges() {
        Level level1 = new LevelBuilder().priority(1).buildWithRandomId();
        Level level2 = new LevelBuilder().priority(2).buildWithRandomId();
        Level level3 = new LevelBuilder().priority(3).buildWithRandomId();

        Challenge challenge1 = new ChallengeBuilder().level(level1).approved(true).buildWithRandomId();
        Challenge challenge2 = new ChallengeBuilder().level(level1).approved(true).buildWithRandomId();
        Challenge challenge3 = new ChallengeBuilder().level(level2).approved(false).buildWithRandomId();
        Challenge challenge4 = new ChallengeBuilder().level(level2).approved(true).buildWithRandomId();
        Challenge challenge5 = new ChallengeBuilder().level(level2).approved(true).buildWithRandomId();
        Challenge challenge6 = new ChallengeBuilder().level(level3).approved(true).buildWithRandomId();

        Solution solution1 = new SolutionBuilder().challenge(challenge1).buildWithRandomId();
        Solution solution2 = new SolutionBuilder().challenge(challenge2).buildWithRandomId();

        User user = new UserBuilder().buildWithRandomId();

        List<Level> levels = Arrays.asList(level1, level2, level3);
        when(solutionService.getAllSolutionsInLevelsOf(user, levels)).thenReturn(Arrays.asList(solution1, solution2));

        CurrentLevelModel currentLevelModel = helper.prepareCurrentLevelModelFor(user, levels);

        assertThat(currentLevelModel.getCurrentLevel(), equalTo(level2));
        assertThat(currentLevelModel.getNextLevel(), equalTo(level3));
        assertThat(currentLevelModel.getChallengeCountToNextLevel(), equalTo(2));
        assertExpectedItems(currentLevelModel.getSolutions(), solution1, solution2);
    }

}
