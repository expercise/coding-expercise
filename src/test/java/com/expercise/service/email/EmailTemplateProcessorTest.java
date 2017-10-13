package com.expercise.service.email;

import com.expercise.BaseSpringIntegrationTest;
import com.expercise.testutils.FileTestUtils;
import com.expercise.testutils.builder.UserBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class EmailTemplateProcessorTest extends BaseSpringIntegrationTest {

    private static final String FORGOT_MY_PASSWORD_EMAIL = "forgotMyPasswordEmail";

    @Autowired
    private EmailTemplateProcessor processor;

    @Test
    public void shouldRenderForgotMyPasswordEmail() {
        Map<String, Object> params = new HashMap<>();
        params.put("user", new UserBuilder().id(1L).email("user@expercise.com").firstName("Ahmet").lastName("Mehmet").build());
        params.put("url", "http://www.expercise.com/resetUrl?token=123qwe");

        String renderedEmail = processor.createEmail(FORGOT_MY_PASSWORD_EMAIL, params);

        assertEmail(renderedEmail, contentOf(FORGOT_MY_PASSWORD_EMAIL));
    }

    private void assertEmail(String actual, String expected) {
        assertThat(strip(actual), equalTo(strip(expected)));
    }

    private String contentOf(String templateFile) {
        return strip(FileTestUtils.getFileContentFrom("/renderedEmails/" + templateFile + ".html"));
    }

    private String strip(String str) {
        return Arrays.stream(str.split(System.lineSeparator()))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.joining(System.lineSeparator()));
    }

}