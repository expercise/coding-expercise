package com.ufukuzun.kodility.utils;

import com.ufukuzun.kodility.exception.KodilityGenericException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    public static final int ONE_DAY = 60 * 60 * 24;

    private static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";

    private static final String DATE_AND_TIME_FORMAT = "dd/MM/yyyy HH:mm";

    private static final String DATE_AND_TIME_WITH_NAMED_MONTH_FORMAT = "dd MMMM yyyy HH:mm";

    private DateUtils() {
    }

    public static Date toDate(String date) {
        return parseDate(date, new SimpleDateFormat(SIMPLE_DATE_FORMAT));
    }

    public static Date toDateTime(String date) {
        return parseDate(date, new SimpleDateFormat(DATE_AND_TIME_FORMAT));
    }

    public static Date toDateTimeWithNamedMonth(String date) {
        return parseDate(date, new SimpleDateFormat(DATE_AND_TIME_WITH_NAMED_MONTH_FORMAT));
    }

    public static String formatDateTimeWithNamedMonth(Date date) {
        return formatDate(date, DATE_AND_TIME_WITH_NAMED_MONTH_FORMAT);
    }

    private static Date parseDate(String date, SimpleDateFormat format) {
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
