package com.icbc.digitalhuman.Utils;

import java.lang.reflect.Field;

public class Judge {
    public static boolean areAllFilled(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value == null) {
                    return false;
                }
                // 如果属性是一个字符串，您还可以检查它是否为空字符串
                if (value instanceof String && ((String) value).isEmpty()) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
