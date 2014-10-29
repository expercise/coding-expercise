package com.ufukuzun.kodility.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Lingo {

    Turkish("tr"),
    English("en");

    private String shortName;

    private Lingo(String shortName) {
        this.shortName = shortName;
    }

    public static Optional<Lingo> getLingo(String shortName) {
        return Arrays.asList(values()).stream()
                .filter(l -> l.getShortName().equals(shortName))
                .findFirst();
    }

    public String getShortName() {
        return shortName;
    }

}
