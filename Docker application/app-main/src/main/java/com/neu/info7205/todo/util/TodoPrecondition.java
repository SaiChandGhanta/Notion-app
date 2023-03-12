package com.neu.info7205.todo.util;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class TodoPrecondition {

    public static void assertEmpty(Map map, ErrorCode errorCode) {
        checkArgument(MapUtils.isEmpty(map), errorCode);
    }

    public static void assertEmpty(Collection collection,ErrorCode errorCode) {
        checkArgument(CollectionUtils.isEmpty(collection), errorCode);
    }

    public static void assertNotEmpty(Map map, ErrorCode errorCode) {
        checkArgument(MapUtils.isNotEmpty(map), errorCode);
    }

    public static void assertNotEmpty(Collection collection, ErrorCode errorCode) {
        checkArgument(CollectionUtils.isNotEmpty(collection), errorCode);
    }

    public static void assertBlank(String string, ErrorCode errorCode) {
        checkArgument(StringUtils.isBlank(string), errorCode);
    }

    public static void assertNotBlank(String string, ErrorCode errorCode) {
        checkArgument(StringUtils.isNotBlank(string), errorCode);
    }

    public static void assertFalse(boolean condition, ErrorCode errorCode) {
        checkArgument(!condition, errorCode);
    }

    public static void assertTrue(boolean condition, ErrorCode errorCode) {
        checkArgument(condition, errorCode);
    }

    public static void assertNull(Object object, ErrorCode errorCode) {
        checkArgument(object == null, errorCode);
    }

    public static void assertNotNull(Object object, ErrorCode errorCode) {
        checkArgument(object != null, errorCode);
    }

    public static void assertEqual(Object object1, Object object2, ErrorCode errorCode) {
        checkArgument(Objects.equals(object1, object2), errorCode);
    }

    public static void assertNotEqual(Object object1, Object object2, ErrorCode errorCode) {
        checkArgument(!Objects.equals(object1, object2), errorCode);
    }

    private static void checkArgument(boolean trueCase, ErrorCode errorCode) {
        if (!trueCase) {
            String errorMsg = null;
            if (errorCode != null) {
                errorMsg =  errorCode.name();
            }
            throw new SystemException(errorMsg, errorCode);
        }
    }
}