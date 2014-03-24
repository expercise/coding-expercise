package com.ufukuzun.kodility.model;

public enum Language {

    JavaScript("js"),
    Python("py");

    private String shortName;

    private Language(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static Language getLanguage(String shortName) {
        for (Language language : values()) {
            if (language.getShortName().equals(shortName)) {
                return language;
            }
        }

        return null;
    }
}
