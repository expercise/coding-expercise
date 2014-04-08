package com.ufukuzun.kodility.interpreter.javascript;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.interpreter.Interpreter;
import com.ufukuzun.kodility.interpreter.InterpreterResult;
import com.ufukuzun.kodility.service.i18n.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Component
public class JavaScriptInterpreter implements Interpreter {

    @Autowired
    private MessageService messageService;

    @Override
    public boolean canInterpret(ProgrammingLanguage programmingLanguage) {
        return ProgrammingLanguage.JavaScript == programmingLanguage;
    }

    @Override
    public InterpreterResult interpret(String solution, Challenge challenge) {
//        ScriptEngine javaScriptEngine = getScriptEngine();
//
//        String script = prepareSourceCode(solution, testCode);
//
//        InterpreterResult result;
//        try {
//            Object evaluationResult = javaScriptEngine.eval(script);
//            if (evaluationResult != null) {
//                result = InterpreterResult.createSuccessResult(evaluationResult.toString());
//            } else {
//                result = InterpreterResult.createFailedResult(messageService.getMessage("interpreter.noResult"));
//            }
//        } catch (ScriptException e) {
//            String errorMessage = prepareErrorMessage(e);   // TODO ufuk: line number wrong in error message
//            result = InterpreterResult.createFailedResult(errorMessage);
//        }
//
        return null;
    }

    private String prepareSourceCode(String solution, String testCode) {
        return solution.concat("\n").concat(testCode);
    }

    private ScriptEngine getScriptEngine() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine javaScriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        javaScriptEngine.put(ScriptEngine.FILENAME, "solution.js");
        return javaScriptEngine;
    }

    private String prepareErrorMessage(ScriptException exception) {
        String message = exception.getMessage();
        String separator = ": ";
        int index = message.lastIndexOf(separator) + separator.length();
        return message.substring(index, message.length());
    }

}
