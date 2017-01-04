package com.teamacronymcoders.tailoredobjects.api.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.lang.reflect.Type;

public class ItemStackDeserializer implements JsonDeserializer<ItemStack> {
    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ResourceLocation registryName;
        Item item = null;
        int metadata = 0;
        int amount = 1;
        JsonObject itemStackJsonObject = json.getAsJsonObject();

        JsonElement registryNameJson = itemStackJsonObject.get("registryName");
        if(registryNameJson != null) {
            registryName = new ResourceLocation(registryNameJson.getAsString());
            item = ForgeRegistries.ITEMS.getValue(registryName);
        }

        if(item == null) {
            throw new JsonParseException("Could not get Item from Item Registry Name");
        }

        JsonElement metadataJson = itemStackJsonObject.get("metadata");
        if(metadataJson != null) {
            metadata = metadataJson.getAsInt();
        }

        JsonElement amountJson = itemStackJsonObject.get("amount");
        if(amountJson != null) {
            amount = amountJson.getAsInt();
        }

        return new ItemStack(item, metadata, amount);
    }
}
