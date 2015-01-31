package com.expercise.service.challenge.action.postaction;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.Solution;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.service.challenge.SolutionService;
import com.expercise.service.challenge.SolutionCountService;
import com.expercise.service.challenge.action.PostEvaluationAction;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.service.user.AuthenticationService;
import com.expercise.utils.Clock;
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
        return PostEvaluationActionOrder.SAVE_USER_SOLUTION.ordinal();
    }

}
