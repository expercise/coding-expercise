package com.expercise.utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public final class UrlUtils {

    private UrlUtils() {
    }

    public static String makeBookmarkable(String text) {
        return Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
                .matcher(Normalizer.normalize(text.replaceAll("[ı]", "i"), Normalizer.Form.NFD))
                .replaceAll("")
                .toLowerCase(Locale.ENGLISH)
                .replaceAll("\\s+", " ")
                .trim()
                .replaceAll(" ", "-")
                .replaceAll("[^a-z0-9\\-]*", "")
                .replaceAll("(\\-)+", "-");
    }

}
