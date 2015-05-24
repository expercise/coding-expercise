package com.expercise.enums;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.expercise.testutils.asserts.Asserts.assertOrderedItems;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocaleContextHolder.class})
public class LingoTest {

    @Before
    public void before() {
        mockStatic(LocaleContextHolder.class);
    }

    @Test
    public void shouldEnglishBeFirstWhenLocaleEnglish() {
        when(LocaleContextHolder.getLocale()).thenReturn(Locale.ENGLISH);

        List<Lingo> resultList = Lingo.sortedLingosByCurrentLocale();

        assertOrderedItems(resultList, Lingo.English, Lingo.Turkish);
    }

    @Test
    public void shouldTurkishBeFirstWhenLocaleTurkish() {
        when(LocaleContextHolder.getLocale()).thenReturn(Lingo.Turkish.getLocale());

        List<Lingo> resultList = Lingo.sortedLingosByCurrentLocale();

        assertOrderedItems(resultList, Lingo.Turkish, Lingo.English);
    }

    @Test
    public void getLingoSafeValueWhenLocaleEnglishButNoValueForEnglish() {
        when(LocaleContextHolder.getLocale()).thenReturn(Locale.ENGLISH);

        Map<Lingo, String> valueMap = new HashMap<>();
        valueMap.put(Lingo.Turkish, "In Turkish");

        String result = Lingo.getValueWithLingoSafe(valueMap);

        assertThat(result, equalTo("In Turkish"));
    }

    @Test
    public void getLingoSafeValueWhenLocaleTurkishButNoValueForTurkish() {
        when(LocaleContextHolder.getLocale()).thenReturn(Lingo.Turkish.getLocale());

        Map<Lingo, String> valueMap = new HashMap<>();
        valueMap.put(Lingo.English, "In English");

        String result = Lingo.getValueWithLingoSafe(valueMap);

        assertThat(result, equalTo("In English"));
    }

    @Test
    public void getLingoSafeValueWhenLocaleEnglishAndThereIsValueForEnglish() {
        when(LocaleContextHolder.getLocale()).thenReturn(Locale.ENGLISH);

        Map<Lingo, String> valueMap = new HashMap<>();
        valueMap.put(Lingo.English, "In English");
        valueMap.put(Lingo.Turkish, "In Turkish");

        String result = Lingo.getValueWithLingoSafe(valueMap);

        assertThat(result, equalTo("In English"));
    }

}