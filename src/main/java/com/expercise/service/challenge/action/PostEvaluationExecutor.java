package com.expercise.service.challenge.action;

import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.service.util.PrioritySorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostEvaluationExecutor {

    @Autowired(required = false)
    private List<PostEvaluationAction> postEvaluationActions = new ArrayList<>();

    public void execute(ChallengeEvaluationContext context) {
        postEvaluationActions.stream()
                .filter(action -> action.canExecute(context))
                .sorted(new PrioritySorter())
                .forEach(a -> a.execute(context));
    }

}
