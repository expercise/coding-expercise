package com.ufukuzun.kodility.interpreter.javascript;

import com.ufukuzun.kodility.controller.challenge.model.SolutionFromUser;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import org.springframework.stereotype.Component;

import javax.script.ScriptException;

@Component
public class JavaScriptInterpreter implements Interpreter {
    @Override
    public boolean canInterpret(ProgrammingLanguage programmingLanguage) {
        return false;
    }

    // TODO ufuk: complete
    @Override
    public InterpreterResult interpret(Solution solutionForChallenge, SolutionFromUser solutionFromUser) {
//        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
//        ScriptEngine javaScriptEngine = scriptEngineManager.getEngineByName("JavaScript");
//        javaScriptEngine.put(ScriptEngine.FILENAME, "solution.js");
//        Object result;
//        try {
//            result = javaScriptEngine.eval(script);
//        } catch (ScriptException e) {
//            result = prepareErrorMessage(e);    // TODO ufuk: line number wrong in error message
//        }
//        return result != null ? result : "No Result";

        return InterpreterResult.createSuccessResult("Success - JavaScript");
    }

    private String prepareErrorMessage(ScriptException exception) {
        String message = exception.getMessage();
        String separator = ": ";
        int index = message.lastIndexOf(separator) + separator.length();
        return message.substring(index, message.length());
    }

}
