package com.expercise.utils;

import java.util.Calendar;
import java.util.Date;

public final class Clock {

    private static Date frozenTime;

    private Clock() {
    }

    public static void freeze(Date frozenTime) {
        Clock.frozenTime = frozenTime;
    }

    public static void unfreeze() {
        frozenTime = null;
    }

    public static Date getTime() {
        return frozenTime != null ? frozenTime : Calendar.getInstance().getTime();
    }

}