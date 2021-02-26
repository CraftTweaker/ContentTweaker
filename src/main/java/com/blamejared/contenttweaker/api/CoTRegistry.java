package com.blamejared.contenttweaker.api;

import com.blamejared.contenttweaker.ContentTweaker;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.functions.ICotFunction;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.crafttweaker.api.*;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.extensions.*;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.*;

public class CoTRegistry {
    
    private final Map<ResourceLocation, IIsCoTBlock> blocks = new LinkedHashMap<>();
    private final Map<ResourceLocation, IIsCotItem> items = new LinkedHashMap<>();
    private final Multimap<ResourceLocation, FunctionEntry> functions = HashMultimap.create();

    public void addBlock(IIsCoTBlock block) {
        if(blocks.containsKey(block.getRegistryName())) {
            CraftTweakerAPI.logError("Registering block '%s' a second time, overriding the first one. Make sure your calls to .register() use unique names!", block.getRegistryName());
        }
        blocks.put(block.getRegistryName(), block);
    }
    
    public void addItem(IIsCotItem item) {
        if(items.containsKey(item.getRegistryName())) {
            CraftTweakerAPI.logError("Registering item '%s' a second time, overriding the first one. Make sure your calls to .register() use unique names!", item.getRegistryName());
        }
        items.put(item.getRegistryName(), item);
    }
    
    public Stream<Block> getBlocksAsVanillaBlocks() {
        return blocks.values().stream().map(IForgeBlock::getBlock);
    }

    public Collection<IIsCoTBlock> getBlocks() {
        return blocks.values();
    }
    
    public Stream<WriteableResource> getAssetResources() {
        return Stream.concat(getBlocks().stream(), getItems().stream()).map(o -> ((IHasResourcesToWrite) o)).flatMap(iHasResourcesToWrite -> iHasResourcesToWrite.getResourcePackResources().stream());
    }
    
    public Stream<WriteableResource> getDataResources() {
        return Stream.concat(getBlocks().stream(), getItems().stream()).flatMap(iHasResourcesToWrite -> iHasResourcesToWrite.getDataPackResources().stream());
    }

    public Collection<IIsCotItem> getItems() {
        return items.values();
    }
    
    public Stream<Item> getItemsAsVanillaItems() {
        return getItems().stream().map(IForgeItem::getItem);
    }

    public IIsCotItem getItem(ResourceLocation resourceLocation) {
        return Optional.ofNullable(items.get(resourceLocation)).orElseThrow(() -> new IllegalArgumentException("Could find CoT item for " + resourceLocation.toString()));
    }

    public IIsCoTBlock getBlock(ResourceLocation resourceLocation) {
        return Optional.ofNullable(blocks.get(resourceLocation)).orElseThrow(() -> new IllegalArgumentException("Could find CoT block for " + resourceLocation.toString()));
    }

    public IIsCotItem getItem(String location) {
        return getItem(new ResourceLocation(ContentTweaker.MOD_ID, location));
    }

    public IIsCoTBlock getBlock(String location) {
        return getBlock(new ResourceLocation(ContentTweaker.MOD_ID, location));
    }

    public <T> Optional<T> getFunction(IHasResourceLocation hasResourceLocation, Class<T> functionType) {
        return functions.get(hasResourceLocation.getRegistryName())
                .stream()
                .filter(functionEntry -> functionEntry.isTypeEqual(functionType))
                .map(FunctionEntry::getFunction)
                .filter(functionType::isInstance)
                .map(functionType::cast)
                .findFirst();
    }

    public void putFunction(IHasResourceLocation hasResourceLocation, ICotFunction function, Type functionType) {
        functions.put(hasResourceLocation.getRegistryName(), new FunctionEntry(function, functionType));
    }

    public void removeFunction(IHasResourceLocation hasResourceLocation, Type functionType) {
        functions.get(hasResourceLocation.getRegistryName()).removeIf(functionEntry -> functionEntry.isTypeEqual(functionType));
    }

    public static class FunctionEntry {
        private final ICotFunction function;
        private final Type type;

        public FunctionEntry(ICotFunction function, Type type) {
            this.function = function;
            this.type = type;
        }

        public ICotFunction getFunction() {
            return function;
        }

        public Type getType() {
            return type;
        }

        public boolean isTypeEqual(Type type) {
            return this.type.getTypeName().equals(type.getTypeName());
        }
    }
}
