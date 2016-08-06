package com.expercise.interpreter.typechecker.javascript;

public class BasicJavaTypeWrapper {

    private Class<?> javaClass;

    public BasicJavaTypeWrapper(Class<?> javaClass) {
        this.javaClass = javaClass;
    }

    boolean valid(Object value) {
        return javaClass.isInstance(value);
    }

}
