package com.teamacronymcoders.tailoredobjects.api.deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.teamacronymcoders.tailoredobjects.api.json.JsonRequiredDeserializer;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class DeserializerBase<OBJECT> implements IDeserializer {
    protected Gson gson;
    private String name;
    private Class<OBJECT> clazz;
    private Function<OBJECT, Boolean> registerFunction;

    public DeserializerBase(String name, Class<OBJECT> objectClass, Function<OBJECT, Boolean> registerFunction) {
        this.name = name;
        this.clazz = objectClass;
        this.gson = new GsonBuilder().registerTypeAdapter(objectClass, new JsonRequiredDeserializer<OBJECT>()).create();
        this.registerFunction = registerFunction;
    }

    @Nonnull
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean deserializeObject(@Nonnull JsonObject jsonObject) {
        OBJECT object = this.gson.fromJson(jsonObject, this.clazz);
        return object != null && this.registerFunction.apply(object);
    }
}
