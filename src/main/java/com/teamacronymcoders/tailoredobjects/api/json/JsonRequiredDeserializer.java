package com.teamacronymcoders.tailoredobjects.api.json;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.tailoredobjects.TailoredObjects;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class JsonRequiredDeserializer<T> implements JsonDeserializer<T> {
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        T pojo = new Gson().fromJson(je, type);
        checkClassForRequired(pojo, pojo.getClass());
        return pojo;
    }

    private void checkClassForRequired(T object, Class clazz) throws JsonParseException {
        if(clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                if (f.getAnnotation(JsonRequired.class) != null) {
                    try {
                        f.setAccessible(true);
                        if (f.get(object) == null) {
                            throw new JsonParseException("Missing field in JSON: " + f.getName());
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        TailoredObjects.instance.getLogger().getLogger().error("", ex);
                    }
                }
            }
            checkClassForRequired(object, clazz.getSuperclass());
        }
    }
}
