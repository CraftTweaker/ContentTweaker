package com.teamacronymcoders.tailoredobjects.api.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Reflection {
    public static <T> Map<String, T> getStaticFieldsOfType(Class<T> fieldClazz, Class objectClass) {
        Map<String, T> results = new HashMap<>();
        Field[] fields = objectClass.getFields();
        if(fields != null && fields.length > 0) {
            for(Field field: fields) {
                if(Modifier.isStatic(field.getModifiers())) {
                    try {
                        Object object = field.get(null);
                        if(fieldClazz.isInstance(object)) {
                            results.put(field.getName(), fieldClazz.cast(object));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return results;
    }

}
