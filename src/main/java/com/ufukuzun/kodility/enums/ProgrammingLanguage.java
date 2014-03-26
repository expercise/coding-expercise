package com.ufukuzun.kodility.enums;

public enum ProgrammingLanguage {

    JavaScript("js"),
    Python("py");

    private String shortName;

    private ProgrammingLanguage(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static ProgrammingLanguage getLanguage(String shortName) {
        for (ProgrammingLanguage programmingLanguage : values()) {
            if (programmingLanguage.getShortName().equals(shortName)) {
                return programmingLanguage;
            }
        }
        return null;
    }
}
