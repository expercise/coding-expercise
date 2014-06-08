package com.ufukuzun.kodility.testutils;

import com.ufukuzun.kodility.exception.KodilityGenericException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TestDateUtils {

    private TestDateUtils() {
    }

    public static Date toDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");
        return parse(date, format);
    }

    private static Date parse(String date, SimpleDateFormat format) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new KodilityGenericException("Date format exception in TestDateUtils:" + date, e);
        }
    }

}
