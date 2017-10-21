package com.expercise.utils;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

public final class RandomTextUtils {

    private RandomTextUtils() {
    }

    public static String alphabetic(int length) {
        return new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(CharacterPredicates.LETTERS)
                .build()
                .generate(length);
    }

}
