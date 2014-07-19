package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.AbstractDaoTest;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Level;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.service.challenge.model.CurrentLevelModel;
import com.ufukuzun.kodility.testutils.builder.ChallengeBuilder;
import com.ufukuzun.kodility.testutils.builder.LevelBuilder;
import com.ufukuzun.kodility.testutils.builder.SolutionBuilder;
import com.ufukuzun.kodility.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static com.ufukuzun.kodility.testutils.asserts.Asserts.assertExpectedItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrentLevelHelperTest extends AbstractDaoTest {

    @InjectMocks
    private CurrentLevelHelper helper;

    @Mock
    private LevelService levelService;

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

        when(levelService.getAllLevelsInOrder()).thenReturn(Arrays.asList(level1, level2, level3));
        when(solutionService.getAllApprovedChallengeSolutionsOf(user)).thenReturn(Arrays.asList(solution1, solution2));

        CurrentLevelModel currentLevelModel = helper.prepareCurrentLevelModelFor(user);

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

        when(levelService.getAllLevelsInOrder()).thenReturn(Arrays.asList(level1, level2));
        when(solutionService.getAllApprovedChallengeSolutionsOf(user)).thenReturn(Arrays.asList(solution1, solution2, solution3));

        CurrentLevelModel currentLevelModel = helper.prepareCurrentLevelModelFor(user);

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

        when(levelService.getAllLevelsInOrder()).thenReturn(Arrays.asList(level1, level2, level3));
        when(solutionService.getAllApprovedChallengeSolutionsOf(user)).thenReturn(Arrays.asList(solution1, solution2));

        CurrentLevelModel currentLevelModel = helper.prepareCurrentLevelModelFor(user);

        assertThat(currentLevelModel.getCurrentLevel(), equalTo(level2));
        assertThat(currentLevelModel.getNextLevel(), equalTo(level3));
        assertThat(currentLevelModel.getChallengeCountToNextLevel(), equalTo(2));
        assertExpectedItems(currentLevelModel.getSolutions(), solution1, solution2);
    }

}
