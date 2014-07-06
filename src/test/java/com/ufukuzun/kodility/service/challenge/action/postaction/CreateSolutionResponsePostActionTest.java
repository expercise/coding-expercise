package com.ufukuzun.kodility.service.challenge.action.postaction;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.challenge.UserPointService;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.i18n.MessageService;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import com.ufukuzun.kodility.testutils.builder.ChallengeBuilder;
import com.ufukuzun.kodility.testutils.builder.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateSolutionResponsePostActionTest {

    @InjectMocks
    private CreateSolutionResponsePostAction action;

    @Mock
    private MessageService messageService;

    @Mock
    private UserPointService userPointService;

    @Mock
    private AuthenticationService authenticationService;

    private ChallengeEvaluationContext context;
    private User user;

    @Before
    public void init() {
        context = new ChallengeEvaluationContext();
        Challenge challenge = new ChallengeBuilder().id(1L).point(22).user(new UserBuilder().id(2L).build()).build();
        context.setChallenge(challenge);
        context.setLanguage(ProgrammingLanguage.Python);

        user = new UserBuilder().id(3L).build();
        when(authenticationService.getCurrentUser()).thenReturn(user);
    }

    @Test
    public void shouldExecuteEveryTime() {
        assertTrue(action.canExecute(new ChallengeEvaluationContext()));
    }

    @Test
    public void shouldCreateSuccessResponseWhenInterpreterResultIsSuccess() {
        context.setInterpreterResult(InterpreterResult.createSuccessResult());

        when(userPointService.canUserWinPoint(context.getChallenge(), user, context.getLanguage())).thenReturn(true);
        when(messageService.getMessage("challenge.successwithpoint", 22)).thenReturn("success, 22 points");

        action.execute(context);

        assertTrue(context.getSolutionValidationResult().isSuccess());
        assertThat(context.getSolutionValidationResult().getResult(), equalTo("success, 22 points"));
    }

    @Test
    public void shouldCreateFailedResponseWhenInterpreterResultIsFailed() {
        context.setInterpreterResult(InterpreterResult.createFailedResult());

        when(messageService.getMessage("challenge.failed")).thenReturn("failed");

        action.execute(context);

        assertFalse(context.getSolutionValidationResult().isSuccess());
        assertThat(context.getSolutionValidationResult().getResult(), equalTo("failed"));
    }

    @Test
    public void shouldCreateSuccessfulWithoutPointWinningMessageIfUserHadAlreadyWonPoint() {
        context.setInterpreterResult(InterpreterResult.createSuccessResult());

        when(messageService.getMessage("challenge.success")).thenReturn("success");
        when(userPointService.canUserWinPoint(context.getChallenge(), user, context.getLanguage())).thenReturn(false);

        action.execute(context);

        assertTrue(context.getSolutionValidationResult().isSuccess());
        assertThat(context.getSolutionValidationResult().getResult(), equalTo("success"));
    }

}
