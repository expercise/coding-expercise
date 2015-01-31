package com.expercise.testutils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public final class FileTestUtils {

    private FileTestUtils() {
    }

    public static String getFileContentFrom(String file) {
        InputStream inputStream = FileTestUtils.class.getResourceAsStream(file);
        try {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("File couldn't be read: " + file);
        }
    }

}
