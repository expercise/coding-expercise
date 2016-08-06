package com.expercise.enums;

import com.expercise.utils.JsonUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public enum DataType {

    Integer {
        @Override
        public Object convert(String rawValue) {
            return JsonUtils.fromJson(rawValue, Integer.class);
        }
    },

    Text {
        @Override
        public Object convert(String rawValue) {
            return JsonUtils.fromJson(rawValue, String.class);
        }
    },

    Array {
        @Override
        public Object convert(String rawValue) {
            return JsonUtils.fromJson(rawValue, List.class);
        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(DataType.class);

    public abstract Object convert(String rawValue);

    public boolean isProperTypeFor(String rawValue) {
        try {
            convert(rawValue);
        } catch (Exception e) {
            LOGGER.debug("Exception while converting {} to {}", rawValue, this.name(), e);
            return false;
        }
        return true;
    }

    public boolean isNotProperTypeFor(String rawValue) {
        return !isProperTypeFor(rawValue);
    }

    public static String toLiteral(Object object) {
        if (object instanceof String) {
            return "\"" + StringEscapeUtils.escapeJava(object.toString()) + "\"";
        } else {
            return object.toString();
        }
    }
}
