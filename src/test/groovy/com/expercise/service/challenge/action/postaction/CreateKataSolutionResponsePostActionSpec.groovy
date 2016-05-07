package com.expercise.service.challenge.action.postaction
import com.expercise.domain.challenge.Challenge
import com.expercise.domain.challenge.ChallengeType
import com.expercise.domain.challenge.TestCase
import com.expercise.domain.user.User
import com.expercise.enums.ProgrammingLanguage
import com.expercise.interpreter.InterpreterResult
import com.expercise.interpreter.TestCaseResult
import com.expercise.interpreter.TestCaseWithResult
import com.expercise.service.challenge.LeaderBoardService
import com.expercise.service.challenge.UserPointService
import com.expercise.service.challenge.UserTestCaseStateService
import com.expercise.service.challenge.model.ChallengeEvaluationContext
import com.expercise.service.challenge.model.ChallengeSolutionStatus
import com.expercise.service.i18n.MessageService
import com.expercise.service.user.AuthenticationService
import com.expercise.testutils.builder.ChallengeBuilder
import com.expercise.testutils.builder.UserBuilder
import spock.lang.Specification

class CreateKataSolutionResponsePostActionSpec extends Specification {

    CreateKataSolutionResponsePostAction action

    MessageService messageService = Mock()
    AuthenticationService authenticationService = Mock()
    UserPointService userPointService = Mock()
    UserTestCaseStateService userTestCaseStateService = Mock()
    LeaderBoardService leaderBoardService = Mock()

    def setup() {
        def dependencies = [messageService: messageService,
                            authenticationService: authenticationService,
                            userPointService: userPointService,
                            userTestCaseStateService: userTestCaseStateService,
                            leaderBoardService : leaderBoardService]
        action = new CreateKataSolutionResponsePostAction(dependencies)
    }

    def "should execute for only code kata type challenges"() {
        given:
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        Challenge codeKataChallenge = new ChallengeBuilder().challengeType(ChallengeType.CODE_KATA).buildWithRandomId()
        context.setChallenge(codeKataChallenge)

        expect:
        action.canExecute(context)
    }

    def "should not execute for challenges that has different type from code kata"() {
        given:
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        Challenge nonKataChallenge = new ChallengeBuilder().challengeType(ChallengeType.ALGORITHM).buildWithRandomId()
        context.setChallenge(nonKataChallenge)

        expect:
        !action.canExecute(context)
    }

    def "should set result as successful if kata challenge completed properly"() {
        given: "create the challenge"
        TestCase testCase0 = new TestCase(id:1L)
        TestCase testCase1 = new TestCase(id:2L)
        Challenge codeKataChallenge = new ChallengeBuilder()
                .challengeType(ChallengeType.CODE_KATA)
                .testCases(testCase0, testCase1)
                .point(10).buildWithRandomId()

        and: "prepare the challenge context"
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(codeKataChallenge)
        context.setLanguage(ProgrammingLanguage.Python)
        def testCaseWithResult0 = new TestCaseWithResult(testCase0)
            testCaseWithResult0.setActualValue("0")
            testCaseWithResult0.setTestCaseResult(TestCaseResult.PASSED)
        def testCaseWithResult1 = new TestCaseWithResult(testCase1)
            testCaseWithResult1.setActualValue("1")
            testCaseWithResult1.setTestCaseResult(TestCaseResult.PASSED)
        context.addTestCaseWithResult(testCaseWithResult0)
        context.addTestCaseWithResult(testCaseWithResult1)

        and: "prepare interpreter result"
        context.setInterpreterResult(InterpreterResult.createSuccessResult())

        and: "prepare message stubbing"

        User user = new UserBuilder().buildWithRandomId()
        1 * authenticationService.getCurrentUser() >> user
        1 * userPointService.canUserWinPoint(codeKataChallenge, user, ProgrammingLanguage.Python) >> true
        1 * leaderBoardService.getRankFor(user) >> 1
        1 * messageService.getMessage("challenge.successWithPoint", 10, 1) >> "Congratulations! You won 10 points, right now you are #1 in leaderboard"

        when:
        action.execute(context)

        def solutionResult = context.getSolutionValidationResult()
        then:
        solutionResult.getChallengeSolutionStatus() == ChallengeSolutionStatus.CHALLENGE_COMPLETED
        solutionResult.getResult() == "Congratulations! You won 10 points, right now you are #1 in leaderboard"

        def testCasesWithSourceModel = solutionResult.getTestCasesWithSourceModel()
        testCasesWithSourceModel.getTestCaseModels().size() == 2
        testCasesWithSourceModel.getTestCaseModels().get(0).actualValue == "0"
        testCasesWithSourceModel.getTestCaseModels().get(1).actualValue == "1"
    }

    def "should set result as successful if kata challenge completed but do not give point if user had already won"() {
        given: "create the challenge"
        TestCase testCase0 = new TestCase(id:1L)
        TestCase testCase1 = new TestCase(id:2L)
        Challenge codeKataChallenge = new ChallengeBuilder()
                .challengeType(ChallengeType.CODE_KATA)
                .testCases(testCase0, testCase1)
                .point(10).buildWithRandomId()

        and: "prepare the challenge context"
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(codeKataChallenge)
        context.setLanguage(ProgrammingLanguage.Python)
        def testCaseWithResult0 = new TestCaseWithResult(testCase0)
            testCaseWithResult0.setActualValue("0")
            testCaseWithResult0.setTestCaseResult(TestCaseResult.PASSED)
        def testCaseWithResult1 = new TestCaseWithResult(testCase1)
            testCaseWithResult1.setActualValue("1")
            testCaseWithResult1.setTestCaseResult(TestCaseResult.PASSED)
        context.addTestCaseWithResult(testCaseWithResult0)
        context.addTestCaseWithResult(testCaseWithResult1)

        and: "prepare interpreter result"
        context.setInterpreterResult(InterpreterResult.createSuccessResult())

        and: "prepare message stubbing"
        1 * messageService.getMessage("challenge.success") >> "Congratulations! But you cannot win point"
        User user = new UserBuilder().buildWithRandomId()
        1 * authenticationService.getCurrentUser() >> user
        1 * userPointService.canUserWinPoint(codeKataChallenge, user, ProgrammingLanguage.Python) >> false

        when:
        action.execute(context)

        def solutionResult = context.getSolutionValidationResult()
        then:
        solutionResult.getChallengeSolutionStatus() == ChallengeSolutionStatus.CHALLENGE_COMPLETED
        solutionResult.getResult() == "Congratulations! But you cannot win point"

        def testCasesWithSourceModel = solutionResult.getTestCasesWithSourceModel()
        testCasesWithSourceModel.getTestCaseModels().size() == 2
        testCasesWithSourceModel.getTestCaseModels().get(0).getActualValue() == "0"
        testCasesWithSourceModel.getTestCaseModels().get(1).getActualValue() == "1"
    }

    def "should set result as failed if interpretation result is failed"() {
        given: "create the challenge"
        TestCase testCase0 = new TestCase(id:1L)
        TestCase testCase1 = new TestCase(id:2L)
        Challenge codeKataChallenge = new ChallengeBuilder()
                .challengeType(ChallengeType.CODE_KATA)
                .testCases(testCase0, testCase1)
                .point(10).buildWithRandomId()

        and: "prepare the challenge context"
        ChallengeEvaluationContext context = new ChallengeEvaluationContext();
        context.setChallenge(codeKataChallenge)
        context.setLanguage(ProgrammingLanguage.Python)
        def testCaseWithResult0 = new TestCaseWithResult(testCase0)
            testCaseWithResult0.setActualValue("0")
            testCaseWithResult0.setTestCaseResult(TestCaseResult.PASSED)
        def testCaseWithResult1 = new TestCaseWithResult(testCase1)
            testCaseWithResult1.setActualValue("1")
            testCaseWithResult1.setTestCaseResult(TestCaseResult.FAILED)
        context.addTestCaseWithResult(testCaseWithResult0)
        context.addTestCaseWithResult(testCaseWithResult1)

        and: "prepare interpreter result"
        context.setInterpreterResult(InterpreterResult.createFailedResult())

        and: "prepare message stubbing"
        1 * messageService.getMessage("challenge.failed") >> "Failed!"

        when:
        action.execute(context)

        def solutionResult = context.getSolutionValidationResult()
        then:
        solutionResult.getChallengeSolutionStatus() == ChallengeSolutionStatus.FAILED
        solutionResult.getResult() == "Failed!"

        def testCasesWithSourceModel = solutionResult.getTestCasesWithSourceModel()
        testCasesWithSourceModel.getTestCaseModels().size() == 2
        testCasesWithSourceModel.getTestCaseModels().get(0).getActualValue() == "0"
        testCasesWithSourceModel.getTestCaseModels().get(1).getActualValue() == "1"
    }

}
