package com.ufukuzun.kodility.enums;

public enum DataTypes {

    Integer(java.lang.Integer.class.getName()),
    Text(String.class.getName());

    private String className;

    private DataTypes(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

}
