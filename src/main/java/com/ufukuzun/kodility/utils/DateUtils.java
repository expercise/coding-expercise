package com.ufukuzun.kodility.utils;

import com.ufukuzun.kodility.exception.KodilityGenericException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    private DateUtils() {
    }

    public static Date toDate(String date) {
        return parse(date, new SimpleDateFormat("dd/MM/yyyy"));
    }

    private static Date parse(String date, SimpleDateFormat format) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new KodilityGenericException("Date format exception: " + date, e);
        }
    }

}
