package com.expercise.interpreter;

import org.apache.commons.lang3.StringUtils;

import java.io.StringWriter;

public final class OutputMessageAggregator {

    private static final ThreadLocal<StringWriter> outputMessage = new ThreadLocal<>();

    private static int MAX_BUFFER_SIZE = 256;

    public static void append(String message) {
        assert message != null;

        StringWriter stringWriter = outputMessage.get();
        if (stringWriter == null) {
            StringWriter newStringWriter = new StringWriter();
            if (message.length() > MAX_BUFFER_SIZE) {
                return;
            }
            outputMessage.set(newStringWriter.append(message));
        } else {
            int messageLengthAfterAppending = (stringWriter.getBuffer().length() + message.length());
            if (messageLengthAfterAppending > MAX_BUFFER_SIZE) {
                return;
            }
            stringWriter.append(message);
            outputMessage.set(stringWriter);
        }
    }

    public static String getOutputMessage() {
        StringWriter stringWriter = outputMessage.get();
        outputMessage.remove();
        return stringWriter == null ? StringUtils.EMPTY : stringWriter.toString();
    }

}
