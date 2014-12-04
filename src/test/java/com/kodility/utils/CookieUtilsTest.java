package com.kodility.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CookieUtilsTest {

    @InjectMocks
    private CookieUtils cookieUtils;

    @Mock
    private HttpServletRequest request;

    @Test
    public void shouldGetNullCookieValueForFirstVisitingOfUser() {
        Optional<String> cookieValue = cookieUtils.getCookieValue("cookieName");

        assertThat(cookieValue.isPresent(), equalTo(false));
    }

    @Test
    public void shouldGetCookieValueIfExist() {
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("cookieName", "cookieValue")});

        Optional<String> cookieValue = cookieUtils.getCookieValue("cookieName");

        assertThat(cookieValue.get(), equalTo("cookieValue"));
    }

    @Test
    public void shouldGetNullCookieValueIfNotExist() {
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("cookieName", "cookieValue")});

        Optional<String> cookieValue = cookieUtils.getCookieValue("anotherCookieName");

        assertThat(cookieValue.isPresent(), equalTo(false));
    }

}