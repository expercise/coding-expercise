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

}
