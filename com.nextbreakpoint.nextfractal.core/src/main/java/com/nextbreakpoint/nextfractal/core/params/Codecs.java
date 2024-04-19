package com.nextbreakpoint.nextfractal.core.params;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Codecs {
    public static String encodeDouble(Object value) {
        if (value == null) {
            throw new IllegalStateException("Value is null");
        }
        if (value instanceof Number) {
            return String.valueOf(value);
        }
        throw new IllegalStateException("Unexpected value");
    }

    public static Double decodeDouble(String value) {
        if (value == null) {
            throw new IllegalStateException("Value is null");
        }
        return Double.parseDouble(value);
    }

    public static String encodeString(Object value) {
        if (value == null) {
            throw new IllegalStateException("Value is null");
        }
        if (value instanceof String) {
            return (String) value;
        }
        throw new IllegalStateException("Unexpected value");
    }

    public static String decodeString(String value) {
        if (value == null) {
            throw new IllegalStateException("Value is null");
        }
        return value;
    }
}
