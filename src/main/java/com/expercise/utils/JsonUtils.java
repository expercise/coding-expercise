package com.expercise.utils;

import com.expercise.exception.ExperciseGenericException;
import com.expercise.exception.ExperciseJsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public final class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {
    }

    public static String toJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception while writing the object as JSON string", e);
        }

        return object instanceof Collection ? "[]" : "{}";
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(jsonString, clazz);
        } catch (Exception e) {
            throw new ExperciseGenericException("JSON deserialization exception occurred.", e);
        }
    }

    public static String formatSafely(String jsonString, Class<?> clazz) throws ExperciseJsonException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter();
            defaultPrettyPrinter.indentArraysWith(new DefaultPrettyPrinter.NopIndenter());
            return objectMapper.writer(defaultPrettyPrinter).writeValueAsString(
                    objectMapper.readValue(jsonString, clazz)
            );
        } catch (Exception e) {
            throw new ExperciseJsonException("JSON format exception occurred.", e);
        }
    }

    public static String format(String jsonString) {
        try {
            return formatSafely(jsonString, Object.class);
        } catch (ExperciseJsonException e) {
            throw new ExperciseGenericException("JSON format exception occurred.", e);
        }
    }

}
