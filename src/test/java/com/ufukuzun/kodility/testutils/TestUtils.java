package com.ufukuzun.kodility.testutils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public final class TestUtils {

    public static String getSourceFrom(String sourceFile) {
        InputStream inputStream = TestUtils.class.getResourceAsStream(sourceFile);
        try {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("source read error:" + sourceFile);
        }
    }

}
