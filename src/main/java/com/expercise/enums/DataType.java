package com.expercise.enums;

import com.expercise.exception.ExperciseJsonException;
import com.expercise.utils.JsonUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;

public enum DataType {

    Integer {
        @Override
        public Object toJavaObject(String rawValue) {
            return JsonUtils.fromJson(rawValue, Integer.class);
        }

        @Override
        public void validateJson(String jsonString) throws ExperciseJsonException {
            JsonUtils.formatSafely(jsonString, Integer.class);
        }
    },

    Text {
        @Override
        public Object toJavaObject(String rawValue) {
            return JsonUtils.fromJson(rawValue, String.class);
        }

        @Override
        public void validateJson(String jsonString) throws ExperciseJsonException {
            JsonUtils.formatSafely(jsonString, String.class);
        }
    },

    Array {
        @Override
        public Object toJavaObject(String rawValue) {
            return JsonUtils.fromJson(rawValue, List.class);
        }

        @Override
        public void validateJson(String jsonString) throws ExperciseJsonException {
            JsonUtils.formatSafely(jsonString, List.class);
        }
    };

    public static String toLiteral(Object object) {
        if (object instanceof String) {
            return "\"" + StringEscapeUtils.escapeJava(object.toString()) + "\"";
        } else {
            return object.toString();
        }
    }

    public abstract Object toJavaObject(String rawValue);

    public abstract void validateJson(String jsonString) throws ExperciseJsonException;
}
