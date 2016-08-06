package com.expercise.utils;

import com.expercise.exception.ExperciseGenericException;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class JsonUtilsTest {

    @Test
    public void shouldFormatJsonStringProperlyAccordingToItsType() throws IOException {
        assertThat(JsonUtils.format("  1 "), equalTo("1"));
        assertThat(JsonUtils.format("  \"1\"  "), equalTo("\"1\""));
        assertThat(JsonUtils.format("  [ \"1\", 2   ,3,  [ 2,  \"19\" ]  ]"), equalTo("[\"1\",2,3,[2,\"19\"]]"));
    }

    @Test
    public void shouldThrowJsonProcessingExceptionIfInputIsInvalidJsonType() {
        assertInvalidJsonFormat("\"1");
        assertInvalidJsonFormat("1\"");
        assertInvalidJsonFormat("[1\"");
        assertInvalidJsonFormat("1]");
        assertInvalidJsonFormat(null);
    }

    private void assertInvalidJsonFormat(String input) {
        try {
            JsonUtils.format(input);
            fail();
        } catch (ExperciseGenericException ignored) {
        } catch (Exception e) {
            fail();
        }
    }

}