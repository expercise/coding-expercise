package com.ufukuzun.kodility.service.challenge.action.postaction;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.service.challenge.SolutionService;
import com.ufukuzun.kodility.service.challenge.SolutionCountService;
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

    @Autowired
    private SolutionCountService solutionCountService;

    @Override
    public boolean canExecute(ChallengeEvaluationContext context) {
        return context.getInterpreterResult().isSuccess();
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        Challenge challenge = context.getChallenge();
        User user = authenticationService.getCurrentUser();
        Solution savedSolution = solutionService.getSolutionBy(challenge, user, context.getLanguage());
        String source = context.getSource();
        ProgrammingLanguage language = context.getLanguage();

        if (savedSolution != null) {
            savedSolution.setSolution(source);
            savedSolution.setProgrammingLanguage(language);
            savedSolution.setCreateDate(Clock.getTime());
            solutionService.updateSolution(savedSolution);
            return;
        }

        Solution solution = new Solution();
        solution.setChallenge(challenge);
        solution.setUser(user);
        solution.setCreateDate(Clock.getTime());
        solution.setProgrammingLanguage(language);
        solution.setSolution(source);
        solutionService.saveSolution(solution);

        solutionCountService.clearCacheFor(challenge.getId());
    }

    @Override
    public int getPriority() {
        return 3;
    }

}
