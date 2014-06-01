package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.util.PriorityComparator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PostEvaluationExecutor implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void execute(ChallengeEvaluationContext context) {
        Map<String, PostEvaluationAction> actions = applicationContext.getBeansOfType(PostEvaluationAction.class);

        List<PostEvaluationAction> actionList = new ArrayList<>();
        for (PostEvaluationAction action : actions.values()) {
            if (action.canExecute(context)) {
                actionList.add(action);
            }
        }

        Collections.sort(actionList, PriorityComparator.getInstance());
        for (PostEvaluationAction postEvaluationAction : actionList) {
            postEvaluationAction.execute(context);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
