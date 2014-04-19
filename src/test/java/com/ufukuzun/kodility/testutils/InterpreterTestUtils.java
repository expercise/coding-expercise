package com.ufukuzun.kodility.testutils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public final class InterpreterTestUtils {

    private InterpreterTestUtils() {
    }

    public static String getSourceFrom(String sourceFile) {
        InputStream inputStream = InterpreterTestUtils.class.getResourceAsStream(sourceFile);
        try {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Source code file couldn't be read: " + sourceFile);
        }
    }

}
