package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.util.PrioritySorter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostEvaluationExecutorTest {

    @InjectMocks
    private PostEvaluationExecutor executor;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private PrioritySorter prioritySorter;

    @Test
    public void shouldExecutePostActionsIfExecutable() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();

        final PostEvaluationAction mockPostAction1 = mock(PostEvaluationAction.class);
        final PostEvaluationAction mockPostAction2 = mock(PostEvaluationAction.class);

        Map<String, PostEvaluationAction> postActionMap = new HashMap<String, PostEvaluationAction>() {{
            put("mockPostAction1", mockPostAction1);
            put("mockPostAction2", mockPostAction2);
        }};

        when(applicationContext.getBeansOfType(PostEvaluationAction.class)).thenReturn(postActionMap);
        when(mockPostAction1.canExecute(context)).thenReturn(false);
        when(mockPostAction2.canExecute(context)).thenReturn(true);

        executor.execute(context);

        InOrder inOrder = inOrder(prioritySorter, mockPostAction2);
        inOrder.verify(prioritySorter).sort(Arrays.asList(mockPostAction2));
        inOrder.verify(mockPostAction2).execute(context);

        verify(mockPostAction1, never()).execute(context);
    }

}
