package com.ufukuzun.kodility.enums;

public enum Lingo {

    Turkish("tr"),
    English("en");

    private String shortName;

    private Lingo(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static Lingo getLingo(String shortName) {
        for (Lingo each : values()) {
            if (each.getShortName().equals(shortName)) {
                return each;
            }
        }
        return null;
    }
}
