package com.ufukuzun.kodility.utils;

public class EnvironmentUtils {

    public static boolean isProduction(String environment) {
        return "production".equals(environment);
    }

}
