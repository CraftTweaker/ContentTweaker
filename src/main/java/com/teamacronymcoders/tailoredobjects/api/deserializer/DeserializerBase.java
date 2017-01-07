package com.teamacronymcoders.tailoredobjects.api.deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.teamacronymcoders.tailoredobjects.api.json.ItemStackDeserializer;
import com.teamacronymcoders.tailoredobjects.api.json.JsonRequiredDeserializer;
import com.teamacronymcoders.tailoredobjects.api.json.MaterialDeserializer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

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
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(objectClass, new JsonRequiredDeserializer<OBJECT>());
        builder.registerTypeAdapter(ItemStack.class, new ItemStackDeserializer());
        builder.registerTypeAdapter(Material.class, new MaterialDeserializer());
        this.gson = builder.create();
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
