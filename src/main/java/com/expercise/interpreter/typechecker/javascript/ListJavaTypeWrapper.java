package com.expercise.interpreter.typechecker.javascript;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.Collection;
import java.util.List;

public class ListJavaTypeWrapper extends BasicJavaTypeWrapper {

    public ListJavaTypeWrapper(Class<?> javaClass) {
        super(javaClass);
    }

    @Override
    boolean valid(Object value) {
        return Collection.class.isInstance(value) ||
                (value instanceof ScriptObjectMirror && List.class.isInstance(((ScriptObjectMirror) value).values()));
    }
}

