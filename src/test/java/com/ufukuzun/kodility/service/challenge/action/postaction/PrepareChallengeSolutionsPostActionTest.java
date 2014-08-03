package com.ufukuzun.kodility.service.challenge.action.postaction;

import com.ufukuzun.kodility.controller.challenge.model.UserSolutionModel;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.challenge.SolutionService;
import com.ufukuzun.kodility.service.challenge.model.ChallengeEvaluationContext;
import com.ufukuzun.kodility.service.challenge.model.SolutionValidationResult;
import com.ufukuzun.kodility.testutils.builder.ChallengeBuilder;
import com.ufukuzun.kodility.testutils.builder.SolutionBuilder;
import com.ufukuzun.kodility.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PrepareChallengeSolutionsPostActionTest {

    @InjectMocks
    private PrepareChallengeSolutionsPostAction action;

    @Mock
    private SolutionService solutionService;

    @Test
    public void shouldExecuteWhenInterpretationIsSuccessful() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        InterpreterResult successResult = new InterpreterResult();
        successResult.setSuccess(true);
        context.setInterpreterResult(successResult);

        assertTrue(action.canExecute(context));
    }

    @Test
    public void shouldNotExecuteWhenInterpretationIsFailed() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        InterpreterResult failedResult = new InterpreterResult();
        failedResult.setSuccess(false);
        context.setInterpreterResult(failedResult);

        assertFalse(action.canExecute(context));
    }

    @Test
    public void shouldAppendSolutionsOfTheChallengeForUserToTheSolutionResult() {
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        Challenge challenge = new ChallengeBuilder().buildWithRandomId();
        context.setChallenge(challenge);
        context.setSolutionValidationResult(SolutionValidationResult.createSuccessResult("success"));

        ArrayList<UserSolutionModel> userSolutionModels = new ArrayList<>();
        Solution solution1 = new SolutionBuilder().solution("my solution 1")
                .programmingLanguage(ProgrammingLanguage.Python).createDate(DateUtils.longFormatToDate("01 October 2012 13:43")).build();
        Solution solution2 = new SolutionBuilder().solution("my solution 2")
                .programmingLanguage(ProgrammingLanguage.JavaScript).createDate(DateUtils.longFormatToDate("14 October 2013 19:12")).build();
        userSolutionModels.add(UserSolutionModel.createFrom(solution1));
        userSolutionModels.add(UserSolutionModel.createFrom(solution2));

        when(solutionService.getUserSolutionModels(context.getChallenge())).thenReturn(userSolutionModels);

        action.execute(context);

        assertThat(context.getSolutionValidationResult().getUserSolutionModels().size(), equalTo(2));

        UserSolutionModel firstUserSolutionModel = context.getSolutionValidationResult().getUserSolutionModels().get(0);
        assertThat(firstUserSolutionModel.getProgrammingLanguage(), equalTo("Python"));
        assertThat(firstUserSolutionModel.getSolution(), equalTo("my solution 1"));
        assertThat(firstUserSolutionModel.getSolutionDate(), equalTo("01 October 2012 13:43"));

        UserSolutionModel secondUserSolutionModel = context.getSolutionValidationResult().getUserSolutionModels().get(1);
        assertThat(secondUserSolutionModel.getProgrammingLanguage(), equalTo("JavaScript"));
        assertThat(secondUserSolutionModel.getSolution(), equalTo("my solution 2"));
        assertThat(secondUserSolutionModel.getSolutionDate(), equalTo("14 October 2013 19:12"));
    }



}