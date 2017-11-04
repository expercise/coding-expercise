package com.expercise.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class NumberUtils {

    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    public static final int DEFAULT_SCALE = 2;

    private NumberUtils() {
    }

    public static int toPercentage(int partial, int total) {
        return BigDecimal.valueOf(partial)
                .divide(BigDecimal.valueOf(total), DEFAULT_SCALE, RoundingMode.CEILING)
                .multiply(ONE_HUNDRED).intValue();
    }

    public static Long parseLong(String text) {
        try {
            return Long.parseLong(text);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Scales and translates a value (x) into a new range [a, b].
     * Formula: f(x) = a + (b - a)(x - min)/(max - min)
     */
    public static BigDecimal scaleAndTranslateIntoRange(BigDecimal value, BigDecimal actualMin, BigDecimal actualMax, BigDecimal desiredMin, BigDecimal desiredMax) {
        return desiredMax.subtract(desiredMin).multiply(value.subtract(actualMin))
                .divide(actualMax.subtract(actualMin), RoundingMode.HALF_UP)
                .add(desiredMin)
                .setScale(2, RoundingMode.HALF_UP);
    }

}
