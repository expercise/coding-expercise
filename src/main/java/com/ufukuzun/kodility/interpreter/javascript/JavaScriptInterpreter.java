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
        Object result = null;
        try {
            result = javaScriptEngine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return result;
    }

}
