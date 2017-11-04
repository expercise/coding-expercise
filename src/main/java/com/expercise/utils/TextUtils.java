package com.expercise.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TextUtils {

    private TextUtils() {
    }

    public static String randomAlphabetic(int length) {
        return new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(CharacterPredicates.LETTERS)
                .build()
                .generate(length);
    }

    public static List<String> splitSemicolonSeperated(String value) {
        String[] parts = StringUtils.split(StringUtils.defaultString(value), ";");
        return new ArrayList<>(Arrays.asList(parts));
    }

}
