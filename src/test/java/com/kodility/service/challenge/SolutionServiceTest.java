package com.kodility.service.challenge;

import com.kodility.dao.challenge.SolutionDao;
import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.Solution;
import com.kodility.domain.level.Level;
import com.kodility.domain.user.User;
import com.kodility.enums.ProgrammingLanguage;
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
import java.util.Collections;
import java.util.List;

import static com.kodility.testutils.asserts.Asserts.assertExpectedItems;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SolutionServiceTest {

    @InjectMocks
    private SolutionService service;

    @Mock
    private SolutionDao solutionDao;

    @Test
    public void shouldSaveSolution() {
        Solution solution = new SolutionBuilder().buildWithRandomId();

        service.saveSolution(solution);

        verify(solutionDao).save(solution);
    }

    @Test
    public void shouldFindSolutionChallengeAndUser() {
        User user = new UserBuilder().buildWithRandomId();
        Challenge challenge = new ChallengeBuilder().buildWithRandomId();
        Solution solution = new SolutionBuilder().challenge(challenge).user(user).buildWithRandomId();

        when(solutionDao.findBy(challenge, user, ProgrammingLanguage.Python)).thenReturn(solution);

        Solution foundSolution = service.getSolutionBy(challenge, user, ProgrammingLanguage.Python);

        assertThat(foundSolution, equalTo(solution));
    }

    @Test
    public void shouldGetAllSolutionsInLevelsOfUser() {
        User user = new UserBuilder().buildWithRandomId();
        Level level = new LevelBuilder().buildWithRandomId();

        Solution solution = new SolutionBuilder().buildWithRandomId();

        when(solutionDao.findAllSolutionsInLevelsOf(user, Arrays.asList(level))).thenReturn(Arrays.asList(solution));

        List<Solution> resultList = service.getAllSolutionsInLevelsOf(user, Arrays.asList(level));

        assertExpectedItems(resultList, solution);
    }

    @Test
    public void shouldReturnEmptyListAsSolutionsInLevelsOfUserIfThereIsNoLevelForTheme() {
        User user = new UserBuilder().buildWithRandomId();

        List<Solution> resultList = service.getAllSolutionsInLevelsOf(user, Collections.emptyList());

        assertTrue(resultList.isEmpty());

        verifyZeroInteractions(solutionDao);
    }

}