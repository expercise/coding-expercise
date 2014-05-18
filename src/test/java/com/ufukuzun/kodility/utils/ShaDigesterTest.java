package com.ufukuzun.kodility.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ShaDigesterTest {

    @InjectMocks
    private ShaDigester shaDigester;

    @Test
    public void shouldDigestMessageWithSha256Algorithm() {
        String result1 = shaDigester.sha256("123qwe");
        String result2 = shaDigester.sha256("123qwe");
        String result3 = shaDigester.sha256("123qwf");

        assertThat(result1, equalTo(result2));
        assertThat(result1, not(equalTo(result3)));
    }
}
