package com.kodility.service.challenge.action.postaction;

import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.challenge.Solution;
import com.kodility.domain.user.User;
import com.kodility.enums.ProgrammingLanguage;
import com.kodility.service.challenge.SolutionService;
import com.kodility.service.challenge.SolutionCountService;
import com.kodility.service.challenge.action.PostEvaluationAction;
import com.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.kodility.service.user.AuthenticationService;
import com.kodility.utils.Clock;
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
