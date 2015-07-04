package com.expercise.interpreter;

import org.apache.commons.lang3.StringUtils;

import java.io.StringWriter;

public final class OutputMessageAggregator {

    private static final ThreadLocal<StringWriter> outputMessage = new ThreadLocal<>();

    public static void append(String message) {
        StringWriter stringWriter = outputMessage.get();
        if (stringWriter == null) {
            StringWriter newStringWriter = new StringWriter();
            outputMessage.set(newStringWriter.append(message));
        } else {
            stringWriter.append(message);
            outputMessage.set(stringWriter);
        }
    }

    public static String getOutputMessage() {
        StringWriter stringWriter = outputMessage.get();
        return stringWriter == null ? StringUtils.EMPTY : stringWriter.toString();
    }

}
