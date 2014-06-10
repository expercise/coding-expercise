package com.ufukuzun.kodility.service.challenge.action.postaction;

import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.service.challenge.SolutionService;
import com.ufukuzun.kodility.service.challenge.action.PostEvaluationAction;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import com.ufukuzun.kodility.utils.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveUserSolutionPostAction implements PostEvaluationAction {

    @Autowired
    private SolutionService solutionService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean canExecute(ChallengeEvaluationContext context) {
        return context.getInterpreterResult().isSuccess();
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        Solution solution = new Solution();
        solution.setChallenge(context.getChallenge());
        solution.setUser(authenticationService.getCurrentUser());
        solution.setCreateDate(Clock.getTime());
        solution.setProgrammingLanguage(context.getLanguage());
        solution.setSolution(context.getSource());
        solutionService.saveSolution(solution);
    }

    @Override
    public int getPriority() {
        return 3;
    }

}
