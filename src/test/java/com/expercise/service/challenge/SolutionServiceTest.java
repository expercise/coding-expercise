package com.expercise.service.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.Solution;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.repository.challenge.SolutionRepository;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.SolutionBuilder;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

}