package com.expercise.enums;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Lingo {

    Turkish("tr", Locale.forLanguageTag("tr")),
    English("en", Locale.ENGLISH),
    Portuguese("pt", Locale.forLanguageTag("pt"));

    private String shortName;

    private Locale locale;

    Lingo(String shortName, Locale locale) {
        this.shortName = shortName;
        this.locale = locale;
    }

    public static Optional<Lingo> getLingo(String shortName) {
        return Arrays.asList(values()).stream()
                .filter(l -> shortName.startsWith(l.getShortName()))
                .findFirst();
    }

    public static Lingo getCurrentLingo() {
        return getLingo(LocaleContextHolder.getLocale().toString()).get();
    }

    public static List<Lingo> sortedLingosByCurrentLocale() {
        return Stream.of(values())
                .sorted((l1, l2) -> {
                    String currentLocale = LocaleContextHolder.getLocale().toString();
                    return currentLocale.startsWith(l1.getShortName()) ? -1 : l1.compareTo(l2);
                }).collect(Collectors.toList());
    }

    public static String getValueFrom(Map<Lingo, String> source) {
        return source.get(getCurrentLingo());
    }

    public static String getValueWithLingoSafe(Map<Lingo, String> source) {
        for (Lingo lingo : sortedLingosByCurrentLocale()) {
            String value = source.get(lingo);
            if (StringUtils.isNotBlank(value)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Lingo safe value could not be found!");
    }

    public String getShortName() {
        return shortName;
    }

    public Locale getLocale() {
        return locale;
    }

}
