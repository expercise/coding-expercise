package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class PostEvaluationExecutorTest {

    @InjectMocks
    private PostEvaluationExecutor executor;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private PostEvaluationAction mockPostAction1;

    @Mock
    private PostEvaluationAction mockPostAction2;

    @Mock
    private PostEvaluationAction mockPostAction3;

    @Before
    public void init() {
        when(mockPostAction1.canExecute(any(ChallengeEvaluationContext.class))).thenReturn(false);
        when(mockPostAction1.getPriority()).thenReturn(1);

        when(mockPostAction2.canExecute(any(ChallengeEvaluationContext.class))).thenReturn(true);
        when(mockPostAction2.getPriority()).thenReturn(3);

        when(mockPostAction3.canExecute(any(ChallengeEvaluationContext.class))).thenReturn(true);
        when(mockPostAction3.getPriority()).thenReturn(2);
    }

    @Test
    public void shouldExecutePostActionsByPriorityIfExecutable() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();

        Map<String, PostEvaluationAction> postActionMap = new HashMap<String, PostEvaluationAction>() {{
            put("mockPostAction1", mockPostAction1);
            put("mockPostAction2", mockPostAction2);
            put("mockPostAction3", mockPostAction3);
        }};

        when(applicationContext.getBeansOfType(PostEvaluationAction.class)).thenReturn(postActionMap);

        executor.execute(context);

        verify(mockPostAction1, times(0)).execute(context);
        verify(mockPostAction2).execute(context);

        InOrder inOrder = inOrder(mockPostAction3, mockPostAction2);
        inOrder.verify(mockPostAction3).execute(context);
        inOrder.verify(mockPostAction2).execute(context);
    }

}
