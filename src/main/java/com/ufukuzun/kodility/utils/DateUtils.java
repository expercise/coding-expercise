package com.ufukuzun.kodility.utils;

import com.ufukuzun.kodility.exception.KodilityGenericException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    private static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";
    private static final String LONG_FORMAT_WITH_NAMED_MONTH = "dd MMMM yyyy HH:mm";

    private DateUtils() {
    }

    public static Date toDate(String date) {
        return parse(date, new SimpleDateFormat(SIMPLE_DATE_FORMAT));
    }

    public static Date longFormatToDate(String date) {
        return parse(date, new SimpleDateFormat(LONG_FORMAT_WITH_NAMED_MONTH));
    }

    public static String formatDateToLongFormat(Date date) {
        return formatDate(date, LONG_FORMAT_WITH_NAMED_MONTH);
    }

    private static Date parse(String date, SimpleDateFormat format) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new KodilityGenericException("Date format exception: " + date, e);
        }
    }

    private static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

}
