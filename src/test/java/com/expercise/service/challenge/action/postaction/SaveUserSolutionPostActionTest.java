package com.expercise.service.challenge.action.postaction;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.Solution;
import com.expercise.domain.challenge.TestCase;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.interpreter.InterpreterResult;
import com.expercise.interpreter.TestCaseWithResult;
import com.expercise.service.challenge.SolutionCountService;
import com.expercise.service.challenge.SolutionService;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.service.user.AuthenticationService;
import com.expercise.testutils.builder.ChallengeBuilder;
import com.expercise.testutils.builder.SolutionBuilder;
import com.expercise.testutils.builder.UserBuilder;
import com.expercise.utils.Clock;
import com.expercise.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SaveUserSolutionPostActionTest {

    @InjectMocks
    private SaveUserSolutionPostAction action;

    @Mock
    private SolutionService solutionService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private SolutionCountService solutionCountService;

    @Test
    public void shouldBeAbleToExecuteIfEvaluationIsSucceed() {
        TestCase testCase = new TestCase();
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(new ChallengeBuilder().testCases(testCase).buildWithRandomId());
        context.setInterpreterResult(InterpreterResult.createSuccessResult());
        context.addTestCaseWithResult(new TestCaseWithResult(testCase));

        when(authenticationService.isCurrentUserAuthenticated()).thenReturn(true);

        assertTrue(action.canExecute(context));
    }

    @Test
    public void shouldNotBeAbleToExecuteIfEvaluationIsFailed() {
        TestCase testCase = new TestCase();
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(new ChallengeBuilder().testCases(testCase).buildWithRandomId());
        context.setInterpreterResult(InterpreterResult.createFailedResult());
        context.addTestCaseWithResult(new TestCaseWithResult(testCase));

        when(authenticationService.isCurrentUserAuthenticated()).thenReturn(true);

        assertFalse(action.canExecute(context));
    }

    @Test
    public void shouldNotBeAbleToExecuteIfUserNotAuthenticatedEvenIfEvaluationIsSucceed() {
        TestCase testCase = new TestCase();
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(new ChallengeBuilder().testCases(testCase).buildWithRandomId());
        context.setInterpreterResult(InterpreterResult.createSuccessResult());
        context.addTestCaseWithResult(new TestCaseWithResult(testCase));

        when(authenticationService.isCurrentUserAuthenticated()).thenReturn(false);

        assertFalse(action.canExecute(context));
    }

    @Test
    public void shouldSaveUserSolutionIfUserDoesNotHaveSolutionForThatChallengeBefore() {
        Clock.freeze(DateUtils.toDate("10/12/2012"));

        User user = new UserBuilder().email("user@expercise.com").buildWithRandomId();
        Challenge challenge = new ChallengeBuilder().buildWithRandomId();

        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setInterpreterResult(InterpreterResult.createSuccessResult());
        context.setChallenge(challenge);
        context.setLanguage(ProgrammingLanguage.Python);
        context.setSource("this is a solution of the user");

        when(authenticationService.getCurrentUser()).thenReturn(user);

        action.execute(context);

        ArgumentCaptor<Solution> solutionCaptor = ArgumentCaptor.forClass(Solution.class);
        verify(solutionService).saveSolution(solutionCaptor.capture());

        Solution capturedSolution = solutionCaptor.getValue();
        assertThat(capturedSolution.getChallenge(), equalTo(context.getChallenge()));
        assertThat(capturedSolution.getUser(), equalTo(user));
        assertThat(capturedSolution.getCreateDate(), equalTo(Clock.getTime()));
        assertThat(capturedSolution.getSolution(), equalTo("this is a solution of the user"));
        assertThat(capturedSolution.getProgrammingLanguage(), equalTo(ProgrammingLanguage.Python));

        verify(solutionCountService).clearCacheFor(challenge.getId());

        Clock.unfreeze();
    }

    @Test
    public void shouldUpdateUserCurrentSolutionIfUserAlreadyHaveSolutionForThatChallengeBefore() {
        Clock.freeze(DateUtils.toDate("10/12/2012"));

        User user = new UserBuilder().email("user@expercise.com").buildWithRandomId();
        Challenge challenge = new ChallengeBuilder().buildWithRandomId();
        Solution solution = new SolutionBuilder().id(1L).createDate(DateUtils.toDate("09/10/2012")).challenge(challenge).user(user).programmingLanguage(ProgrammingLanguage.Python).build();

        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setInterpreterResult(InterpreterResult.createSuccessResult());
        context.setChallenge(challenge);
        context.setLanguage(ProgrammingLanguage.JavaScript);
        context.setSource("new solution of the user");

        when(authenticationService.getCurrentUser()).thenReturn(user);
        when(solutionService.getSolutionBy(challenge, user, ProgrammingLanguage.JavaScript)).thenReturn(solution);

        action.execute(context);

        ArgumentCaptor<Solution> solutionCaptor = ArgumentCaptor.forClass(Solution.class);
        verify(solutionService).updateSolution(solutionCaptor.capture());

        Solution capturedSolution = solutionCaptor.getValue();
        assertThat(capturedSolution.getId(), equalTo(solution.getId()));
        assertThat(capturedSolution.getChallenge(), equalTo(context.getChallenge()));
        assertThat(capturedSolution.getUser(), equalTo(user));
        assertThat(capturedSolution.getCreateDate(), equalTo(Clock.getTime()));
        assertThat(capturedSolution.getSolution(), equalTo("new solution of the user"));
        assertThat(capturedSolution.getProgrammingLanguage(), equalTo(ProgrammingLanguage.JavaScript));

        verifyZeroInteractions(solutionCountService);

        Clock.unfreeze();
    }

}