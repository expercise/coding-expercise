package com.expercise.service.challenge.action.postaction

import com.expercise.domain.challenge.TestCase
import com.expercise.interpreter.InterpreterResult
import com.expercise.interpreter.TestCaseResult
import com.expercise.interpreter.TestCaseWithResult
import com.expercise.service.challenge.model.ChallengeEvaluationContext
import spock.lang.Specification

class InterpretationFailureCheckForTestCasesPostActionSpec extends Specification {

    InterpretationFailureCheckForTestCasesPostAction action = new InterpretationFailureCheckForTestCasesPostAction()

    def "should check interpretation result against test case if interpretation is failed by syntax error"() {
        given:
        def context = new ChallengeEvaluationContext()
        context.setInterpreterResult(InterpreterResult.syntaxErrorFailedResult("message"))

        expect:
        action.canExecute(context)
    }

    def "should check interpretation result against test case if interpretation is failed by no result error"() {
        given:
        def context = new ChallengeEvaluationContext()
        context.setInterpreterResult(InterpreterResult.noResultFailedResult())

        expect:
        action.canExecute(context)
    }

    def "should not check interpretation result against test case if interpretation is failed by test case values"() {
        given:
        def context = new ChallengeEvaluationContext()
        context.setInterpreterResult(InterpreterResult.createFailedResult())

        expect:
        !action.canExecute(context)
    }

    def "should not check interpretation result against test case if interpretation is successful"() {
        given:
        def context = new ChallengeEvaluationContext()
        context.setInterpreterResult(InterpreterResult.createSuccessResult())

        expect:
        !action.canExecute(context)
    }

    def "should fail all test cases"() {
        given:
        def context = new ChallengeEvaluationContext()
        context.setInterpreterResult(InterpreterResult.syntaxErrorFailedResult("message"))

        def passedTestCases = new TestCaseWithResult(new TestCase())
            passedTestCases.setTestCaseResult(TestCaseResult.PASSED)
        def failedTestCase = new TestCaseWithResult(new TestCase())
            failedTestCase.setTestCaseResult(TestCaseResult.FAILED)
        def newTestCase = new TestCaseWithResult(new TestCase())
            failedTestCase.setTestCaseResult(TestCaseResult.NEW)

        context.addTestCaseWithResult(passedTestCases)
        context.addTestCaseWithResult(newTestCase)
        context.addTestCaseWithResult(failedTestCase)

        when:
        action.execute(context)

        then:
        context.getTestCaseWithResults().size() == 3
        context.getTestCaseWithResults().get(0).testCaseResult == TestCaseResult.FAILED
        context.getTestCaseWithResults().get(1).testCaseResult == TestCaseResult.FAILED
        context.getTestCaseWithResults().get(2).testCaseResult == TestCaseResult.FAILED
    }

}
