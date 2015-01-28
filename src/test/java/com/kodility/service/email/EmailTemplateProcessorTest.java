package com.kodility.service.email;

import com.kodility.EmailTemplateTestsConfiguration;
import com.kodility.testutils.FileTestUtils;
import com.kodility.testutils.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmailTemplateTestsConfiguration.class})
public class EmailTemplateProcessorTest {

    private static final String FORGOT_MY_PASSWORD_EMAIL = "forgotMyPasswordEmail";

    @Autowired
    private EmailTemplateProcessor processor;

    @Test
    public void shouldRenderForgotMyPasswordEmail() {
        Map<String, Object> params = new HashMap<>();
        params.put("user", new UserBuilder().id(1L).email("user@kodility.com").firstName("Ahmet").lastName("Mehmet").build());
        params.put("url", "http://www.kodility.com/resetUrl?token=123qwe");

        String renderedEmail = processor.createEmail(FORGOT_MY_PASSWORD_EMAIL, params);

        assertThat(renderedEmail, equalTo(contentOf(FORGOT_MY_PASSWORD_EMAIL)));
    }

    private String contentOf(String templateFile) {
        return FileTestUtils.getFileContentFrom("/renderedEmails/" + templateFile + ".html");
    }

}