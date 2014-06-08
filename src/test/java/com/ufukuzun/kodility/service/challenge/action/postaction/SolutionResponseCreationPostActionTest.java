package com.ufukuzun.kodility.service.challenge.action.postaction;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.challenge.UserPointService;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.configuration.ConfigurationService;
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
public class SolutionResponseCreationPostActionTest {

    @InjectMocks
    private SolutionResponseCreationPostAction action;

    @Mock
    private MessageService messageService;

    @Mock
    private UserPointService userPointService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private ConfigurationService configurationService;

    private ChallengeEvaluationContext context;
    private User user;

    @Before
    public void init() {
        context = new ChallengeEvaluationContext();
        Challenge challenge = new ChallengeBuilder().id(1L).user(new UserBuilder().id(2L).build()).build();
        context.setChallenge(challenge);

        user = new UserBuilder().id(3L).build();
        when(authenticationService.getCurrentUser()).thenReturn(user);
        when(configurationService.getValueAsInteger("challenge.defaultpointamount")).thenReturn(10);
    }

    @Test
    public void shouldExecuteEveryTime() {
        assertTrue(action.canExecute(new ChallengeEvaluationContext()));
    }

    @Test
    public void shouldCreateSuccessResponseWhenInterpreterResultIsSuccess() {
        context.setInterpreterResult(InterpreterResult.createSuccessResult("success interpreter result"));

        when(userPointService.canUserWinPoint(context.getChallenge(), user)).thenReturn(true);
        when(messageService.getMessage("challenge.success", 10)).thenReturn("success, 10 points");

        action.execute(context);

        assertTrue(context.getSolutionValidationResult().isSuccess());
        assertThat(context.getSolutionValidationResult().getResult(), equalTo("success, 10 points"));
    }

    @Test
    public void shouldCreateFailedResponseWhenInterpreterResultIsFailed() {
        context.setInterpreterResult(InterpreterResult.createFailedResult("failed interpreter result"));

        when(messageService.getMessage("challenge.failed")).thenReturn("failed");

        action.execute(context);

        assertFalse(context.getSolutionValidationResult().isSuccess());
        assertThat(context.getSolutionValidationResult().getResult(), equalTo("failed"));
    }

    @Test
    public void shouldCreateSuccessfulWithoutPointWinningMessageIfUserHadAlreadyWonPoint() {
        context.setInterpreterResult(InterpreterResult.createSuccessResult("success interpreter result"));

        when(messageService.getMessage("challenge.successbutcannotwinpoint")).thenReturn("success but could not won point");
        when(userPointService.canUserWinPoint(context.getChallenge(), user)).thenReturn(false);

        action.execute(context);

        assertTrue(context.getSolutionValidationResult().isSuccess());
        assertThat(context.getSolutionValidationResult().getResult(), equalTo("success but could not won point"));
    }

}