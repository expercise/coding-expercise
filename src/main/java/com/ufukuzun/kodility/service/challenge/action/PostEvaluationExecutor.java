package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.util.PrioritySorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PostEvaluationExecutor {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PrioritySorter prioritySorter;

    public void execute(ChallengeEvaluationContext context) {
        Map<String, PostEvaluationAction> actions = applicationContext.getBeansOfType(PostEvaluationAction.class);

        List<PostEvaluationAction> actionList = new ArrayList<>();
        for (PostEvaluationAction action : actions.values()) {
            if (action.canExecute(context)) {
                actionList.add(action);
            }
        }

        prioritySorter.sort(actionList);
        for (PostEvaluationAction postEvaluationAction : actionList) {
            postEvaluationAction.execute(context);
        }
    }

}
