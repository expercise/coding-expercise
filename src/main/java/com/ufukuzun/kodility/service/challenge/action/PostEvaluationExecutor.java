package com.ufukuzun.kodility.service.challenge.action;

import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.util.PrioritySorter;
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
