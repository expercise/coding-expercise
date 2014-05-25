package com.ufukuzun.kodility.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;

public final class JsonUtils {

    private JsonUtils() {
    }

    public static String toJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        boolean isCollectionObject = object instanceof Collection;
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return isCollectionObject ? "[]" : "{}";
    }

}
