package com.expercise.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DataType {

    Integer(java.lang.Integer.class.getName()) {
        @Override
        public Object convert(String rawValue) {
            return java.lang.Integer.parseInt(rawValue);
        }
    },

    Text(String.class.getName()) {
        @Override
        public Object convert(String rawValue) {
            return rawValue;
        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(DataType.class);

    private String className;

    DataType(String className) {
        this.className = className;
    }

    public abstract Object convert(String rawValue);

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
