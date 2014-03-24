package com.ufukuzun.kodility.interpreter.javascript;

import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Component
public class JavaScriptInterpreter {

    public Object interpret(String script) {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine javaScriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        javaScriptEngine.put(ScriptEngine.FILENAME, "solution.js");
        Object result;
        try {
            result = javaScriptEngine.eval(script);
        } catch (ScriptException e) {
            result = prepareErrorMessage(e);    // TODO ufuk: line number wrong in error message
        }
        return result != null ? result : "No Result";
    }

    private String prepareErrorMessage(ScriptException e) {
        String message = e.getMessage();
        String separator = ": ";
        int index = message.lastIndexOf(separator) + separator.length();
        return message.substring(index, message.length());
    }

}
