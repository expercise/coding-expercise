package com.expercise.utils;

import com.expercise.exception.ExperciseJsonException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class JsonUtilsTest {

    @Test
    public void shouldFormatJsonStringProperlyAccordingToItsType() throws IOException {
        assertThat(JsonUtils.formatSafely("  1 ", Integer.class), equalTo("1"));
        assertThat(JsonUtils.formatSafely("  \"1\"  ", String.class), equalTo("\"1\""));
        assertThat(JsonUtils.formatSafely("  [ \"1\", 2   ,3,  [ 2,  \"19\" ]  ]", List.class), equalTo("[\"1\",2,3,[2,\"19\"]]"));
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
            JsonUtils.formatSafely(input, Object.class);
            fail();
        } catch (ExperciseJsonException ignored) {
        } catch (Exception e) {
            fail();
        }
    }

}