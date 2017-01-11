package com.teamacronymcoders.contenttweaker.api.deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.api.json.content.ItemStackDeserializer;
import com.teamacronymcoders.contenttweaker.api.json.content.MethodDeserializer;
import com.teamacronymcoders.contenttweaker.api.json.content.ResourceDeserializer;
import com.teamacronymcoders.contenttweaker.api.methods.callables.CommandCallable;
import com.teamacronymcoders.contenttweaker.api.methods.callables.ICallableMethod;
import com.teamacronymcoders.contenttweaker.api.methods.returnables.BooleanMethod;
import com.teamacronymcoders.contenttweaker.api.methods.returnables.FloatMethod;
import com.teamacronymcoders.contenttweaker.api.methods.returnables.IBooleanMethod;
import com.teamacronymcoders.contenttweaker.api.methods.returnables.IFloatMethod;
import com.teamacronymcoders.contenttweaker.api.methods.returnables.IIntegerMethod;
import com.teamacronymcoders.contenttweaker.api.methods.returnables.IStringMethod;
import com.teamacronymcoders.contenttweaker.api.methods.returnables.IntegerMethod;
import com.teamacronymcoders.contenttweaker.api.methods.returnables.StringMethod;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class DeserializerBase<OBJECT> implements IDeserializer {
    protected Gson gson;
    private String name;
    private boolean isResource;
    private Class<OBJECT> clazz;
    private Consumer<OBJECT>  registerFunction;

    public DeserializerBase(String name, Class<OBJECT> objectClass, Consumer<OBJECT> registerFunction) {
        this(name, objectClass, false, registerFunction);
    }

    public DeserializerBase(String name, Class<OBJECT> objectClass, boolean isResource, Consumer<OBJECT> registerFunction) {
        this.name = name;
        this.isResource = isResource;
        this.clazz = objectClass;
        GsonBuilder builder = new GsonBuilder();
        //builder.registerTypeAdapter(objectClass, new JsonRequiredDeserializer<OBJECT>());
        builder.registerTypeAdapter(ItemStack.class, new ItemStackDeserializer());
        builder.registerTypeAdapter(IFloatMethod.class, new MethodDeserializer<>(jsonElement -> new FloatMethod(jsonElement.getAsFloat())));
        builder.registerTypeAdapter(IStringMethod.class, new MethodDeserializer<>(jsonElement -> new StringMethod(jsonElement.getAsString())));
        builder.registerTypeAdapter(IIntegerMethod.class, new MethodDeserializer<>(jsonElement -> new IntegerMethod(jsonElement.getAsInt())));
        builder.registerTypeAdapter(IBooleanMethod.class, new MethodDeserializer<>(jsonElement -> new BooleanMethod(jsonElement.getAsBoolean())));
        builder.registerTypeAdapter(ICallableMethod.class, new MethodDeserializer<>(jsonElement -> new CommandCallable(jsonElement.getAsString())));
        ContentTweakerAPI api = ContentTweakerAPI.getInstance();
        builder.registerTypeAdapter(Material.class, new ResourceDeserializer<>(api.getBlockMaterials()));
        builder.registerTypeAdapter(SoundType.class, new ResourceDeserializer<>(api.getSoundTypes()));
        builder.registerTypeAdapter(CreativeTabs.class, new ResourceDeserializer<>(api.getCreativeTabs()));
        this.gson = builder.create();
        this.registerFunction = registerFunction;
    }

    @Nonnull
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isResource() {
        return this.isResource;
    }

    @Override
    public boolean deserializeObject(@Nonnull JsonObject jsonObject) {
        OBJECT object = this.gson.fromJson(jsonObject, this.clazz);
        if(object != null) {
            this.registerFunction.accept(object);
        }
        return object != null;
    }
}
