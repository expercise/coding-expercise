package com.expercise.utils;

public final class EnvironmentUtils {

    private EnvironmentUtils() {
    }

    public static boolean isProduction(String environment) {
        return "prod".equals(environment);
    }

    public static boolean isDevelopment(String environment) {
        return !isProduction(environment);
    }

}
