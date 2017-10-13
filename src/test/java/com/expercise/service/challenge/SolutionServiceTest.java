package com.expercise.service.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.Solution;
import com.expercise.domain.level.Level;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.repository.challenge.SolutionRepository;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.LevelBuilder;
import com.expercise.testutils.builder.SolutionBuilder;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.expercise.testutils.asserts.Asserts.assertExpectedItems;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SolutionServiceTest {

    @InjectMocks
    private SolutionService service;

    @Mock
    private SolutionRepository solutionRepository;

    @Test
    public void shouldSaveSolution() {
        Solution solution = new SolutionBuilder().buildWithRandomId();

        service.saveSolution(solution);

        verify(solutionRepository).save(solution);
    }

    @Test
    public void shouldFindSolutionChallengeAndUser() {
        User user = new UserBuilder().buildWithRandomId();
        Challenge challenge = new ChallengeBuilder().buildWithRandomId();
        Solution solution = new SolutionBuilder().challenge(challenge).user(user).buildWithRandomId();

        when(solutionRepository.findByChallengeAndUserAndProgrammingLanguage(challenge, user, ProgrammingLanguage.Python)).thenReturn(solution);

        Solution foundSolution = service.getSolutionBy(challenge, user, ProgrammingLanguage.Python);

        assertThat(foundSolution, equalTo(solution));
    }

    @Test
    public void shouldGetAllSolutionsInLevelsOfUser() {
        User user = new UserBuilder().buildWithRandomId();
        Level level = new LevelBuilder().buildWithRandomId();

        Solution solution = new SolutionBuilder().buildWithRandomId();

        when(solutionRepository.findByUserAndChallengeLevelInAndChallengeApprovedIsTrue(user, Arrays.asList(level))).thenReturn(Arrays.asList(solution));

        List<Solution> resultList = service.getAllSolutionsInLevelsOf(user, Arrays.asList(level));

        assertExpectedItems(resultList, solution);
    }

    @Test
    public void shouldReturnEmptyListAsSolutionsInLevelsOfUserIfThereIsNoLevelForTheme() {
        User user = new UserBuilder().buildWithRandomId();

        List<Solution> resultList = service.getAllSolutionsInLevelsOf(user, Collections.emptyList());

        assertTrue(resultList.isEmpty());

        verifyZeroInteractions(solutionRepository);
    }

}