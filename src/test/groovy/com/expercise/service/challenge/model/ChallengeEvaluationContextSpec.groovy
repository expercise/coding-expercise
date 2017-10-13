package com.expercise.service.challenge.model

import com.expercise.domain.challenge.Challenge
import com.expercise.domain.challenge.TestCase
import com.expercise.interpreter.InterpreterResult
import com.expercise.interpreter.TestCaseResult
import com.expercise.interpreter.TestCaseWithResult
import spock.lang.Specification

class ChallengeEvaluationContextSpec extends Specification {

    def "should mark challenge as completed if all test cases of challenge are passed"() {
        given:
        def context = new ChallengeEvaluationContext()

        and: "the challenge has 3 test cases"
        TestCase testCase0 = new TestCase();
        TestCase testCase1 = new TestCase();
        TestCase testCase2 = new TestCase();

        and: "create the challenge with tests"
        Challenge challenge = new Challenge();
        challenge.addTestCase(testCase0)
        challenge.addTestCase(testCase1)
        challenge.addTestCase(testCase2)

        and: "test cases under test"
        def resultOfTestCase0 = new TestCaseWithResult(testCase0)
        def resultOfTestCase1 = new TestCaseWithResult(testCase1)
        def resultOfTestCase2 = new TestCaseWithResult(testCase2)

        and: "all test cases are passed in interpreter"
        context.setChallenge(challenge)
        context.addTestCaseWithResult(resultOfTestCase0)
        context.addTestCaseWithResult(resultOfTestCase1)
        context.addTestCaseWithResult(resultOfTestCase2)

        and: "interpretation result is green"
        context.setInterpreterResult(InterpreterResult.createSuccessResult())

        expect:
        context.isChallengeCompleted()
    }

    def "should mark challenge as not completed if all test cases of challenge are not passed yet"() {
        given:
        def context = new ChallengeEvaluationContext()

        and: "the challenge has 3 test cases"
        TestCase testCase0 = new TestCase();
        TestCase testCase1 = new TestCase();
        TestCase testCase2 = new TestCase();

        and: "create the challenge with tests"
        Challenge challenge = new Challenge();
        challenge.addTestCase(testCase0)
        challenge.addTestCase(testCase1)
        challenge.addTestCase(testCase2)

        and: "test cases under test"
        def resultOfTestCase0 = new TestCaseWithResult(testCase0)
        def resultOfTestCase1 = new TestCaseWithResult(testCase1)
        def resultOfTestCase2 = new TestCaseWithResult(testCase2)

        and: "all test cases are passed in interpreter"
        context.setChallenge(challenge)
        context.addTestCaseWithResult(resultOfTestCase0)
        context.addTestCaseWithResult(resultOfTestCase1)
        context.addTestCaseWithResult(resultOfTestCase2)

        and: "interpretation result is red"
        context.setInterpreterResult(InterpreterResult.createFailedResult())

        expect:
        !context.isChallengeCompleted()
    }

    def "should mark challenge as not completed if all test cases of challenge are not completed"() {
        given:
        def context = new ChallengeEvaluationContext()

        and: "the challenge has 3 test cases"
        TestCase testCase0 = new TestCase();
        TestCase testCase1 = new TestCase();
        TestCase testCase2 = new TestCase();

        and: "create the challenge with tests"
        Challenge challenge = new Challenge();
        challenge.addTestCase(testCase0)
        challenge.addTestCase(testCase1)
        challenge.addTestCase(testCase2)

        and: "test cases under test"
        def resultOfTestCase0 = new TestCaseWithResult(testCase0)
        def resultOfTestCase1 = new TestCaseWithResult(testCase1)

        and: "all test cases are passed in interpreter"
        context.setChallenge(challenge)
        context.addTestCaseWithResult(resultOfTestCase0)
        context.addTestCaseWithResult(resultOfTestCase1)

        and: "interpretation result is green"
        context.setInterpreterResult(InterpreterResult.createSuccessResult())

        expect:
        !context.isChallengeCompleted()
    }

    def "should decide interpreter result as success if there is no test case in context"() {
        given:
        def context = new ChallengeEvaluationContext()
        def successTestCase0 = new TestCaseWithResult(new TestCase())
        successTestCase0.setTestCaseResult(TestCaseResult.PASSED)

        def successTestCase1 = new TestCaseWithResult(new TestCase())
        successTestCase1.setTestCaseResult(TestCaseResult.PASSED)

        context.addTestCaseWithResult(successTestCase0)
        context.addTestCaseWithResult(successTestCase1)

        when:
        context.decideInterpreterResult();

        then:
        context.getInterpreterResult().isSuccess()
    }

    def "should decide interpreter result as success if there is no failed test case in context"() {
        given:
        def context = new ChallengeEvaluationContext()

        when:
        context.decideInterpreterResult();

        then:
        context.getInterpreterResult().isSuccess()
    }

    def "should decide interpreter result as failed if there is only one failed test case in context"() {
        given:
        def context = new ChallengeEvaluationContext()
        def failedTestCase = new TestCaseWithResult(new TestCase())
        failedTestCase.setTestCaseResult(TestCaseResult.FAILED)

        def successTestCase = new TestCaseWithResult(new TestCase())
        successTestCase.setTestCaseResult(TestCaseResult.PASSED)

        context.addTestCaseWithResult(failedTestCase)
        context.addTestCaseWithResult(successTestCase)

        when:
        context.decideInterpreterResult();

        then:
        !context.getInterpreterResult().isSuccess()
    }


}
