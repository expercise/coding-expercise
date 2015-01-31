package com.expercise.enums;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public enum Lingo {

    Turkish("tr", Locale.forLanguageTag("tr")),
    English("en", Locale.ENGLISH);

    private String shortName;

    private Locale locale;

    private Lingo(String shortName, Locale locale) {
        this.shortName = shortName;
        this.locale = locale;
    }

    public static Optional<Lingo> getLingo(String shortName) {
        return Arrays.asList(values()).stream()
                .filter(l -> l.getShortName().equals(shortName))
                .findFirst();
    }

    public String getShortName() {
        return shortName;
    }

    public Locale getLocale() {
        return locale;
    }

}
