package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostEvaluationExecutorTest {

    @InjectMocks
    private PostEvaluationExecutor executor;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private MockPostAction1 mockPostAction1;

    @Mock
    private MockPostAction2 mockPostAction2;

    @Test
    public void shouldExecutePostActions() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();

        Map<String, PostEvaluationAction> postActionMap = new HashMap<String, PostEvaluationAction>() {{
            put("mockPostAction1", mockPostAction1);
            put("mockPostAction2", mockPostAction2);
        }};

        when(applicationContext.getBeansOfType(PostEvaluationAction.class)).thenReturn(postActionMap);

        executor.execute(context);

        verify(mockPostAction1).execute(context);
        verify(mockPostAction2).execute(context);
    }

    private class MockPostAction1 implements PostEvaluationAction {
        @Override
        public void execute(ChallengeEvaluationContext context) {}
    }

    private class MockPostAction2 implements PostEvaluationAction {
        @Override
        public void execute(ChallengeEvaluationContext context) {}
    }

}
