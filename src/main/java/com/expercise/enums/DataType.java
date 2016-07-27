package com.expercise.enums;

import com.expercise.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public enum DataType {

    Integer(java.lang.Integer.class.getName()) {
        @Override
        public Object convert(String rawValue) {
            return java.lang.Integer.parseInt(rawValue);
        }

        @Override
        public String toLiteral(String rawValue) {
            return rawValue;
        }
    },

    Text(String.class.getName()) {
        @Override
        public Object convert(String rawValue) {
            return JsonUtils.fromJson(rawValue, String.class);
//            if (rawValue == null) {
//                return "";
//            }
//            return rawValue;
        }

        @Override
        public String toLiteral(String rawValue) {
            if (rawValue == null) {
                return "";
            }
            return "\"" + rawValue + "\"";
        }
    },

    Array(List.class.getName()) {
        @Override
        public Object convert(String rawValue) {
            return JsonUtils.fromJson(rawValue, List.class);
        }

        @Override
        public String toLiteral(String rawValue) {
            return rawValue;
        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(DataType.class);

    private String className;

    DataType(String className) {
        this.className = className;
    }

    public abstract Object convert(String rawValue);

    public abstract String toLiteral(String rawValue);

    public String getClassName() {
        return className;
    }

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

}
