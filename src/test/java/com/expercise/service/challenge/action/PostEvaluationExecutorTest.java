package com.expercise.service.challenge.action;

import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostEvaluationExecutorTest {

    @InjectMocks
    private PostEvaluationExecutor executor;

    @Test
    public void shouldExecutePostActionsIfExecutable() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();

        PostEvaluationAction mockPostAction1 = mock(PostEvaluationAction.class);
        PostEvaluationAction mockPostAction2 = mock(PostEvaluationAction.class);

        ReflectionTestUtils.setField(executor, "postEvaluationActions", Arrays.asList(mockPostAction1, mockPostAction2));

        when(mockPostAction1.canExecute(context)).thenReturn(false);
        when(mockPostAction2.canExecute(context)).thenReturn(true);

        executor.execute(context);

        verify(mockPostAction2).execute(context);
        verify(mockPostAction1, never()).execute(context);
    }

    @Test
    public void shouldExecutePostActionsInOrderByPriority() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();

        PostEvaluationAction mockPostAction1 = mock(PostEvaluationAction.class);
        PostEvaluationAction mockPostAction2 = mock(PostEvaluationAction.class);
        PostEvaluationAction mockPostAction3 = mock(PostEvaluationAction.class);

        ReflectionTestUtils.setField(executor, "postEvaluationActions", Arrays.asList(mockPostAction1, mockPostAction2, mockPostAction3));

        when(mockPostAction1.getPriority()).thenReturn(3);
        when(mockPostAction2.getPriority()).thenReturn(1);
        when(mockPostAction3.getPriority()).thenReturn(2);

        when(mockPostAction1.canExecute(context)).thenReturn(true);
        when(mockPostAction2.canExecute(context)).thenReturn(true);
        when(mockPostAction3.canExecute(context)).thenReturn(true);

        executor.execute(context);

        InOrder inOrder = inOrder(mockPostAction1, mockPostAction2, mockPostAction3);
        inOrder.verify(mockPostAction2).execute(context);
        inOrder.verify(mockPostAction3).execute(context);
        inOrder.verify(mockPostAction1).execute(context);
    }

}
