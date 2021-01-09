package com.blamejared.contenttweaker.api;

import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.crafttweaker.api.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.extensions.*;

import java.util.*;
import java.util.stream.*;

public class CoTRegistry {
    
    private final Map<ResourceLocation, IIsCoTBlock> blocks = new LinkedHashMap<>();
    private final Map<ResourceLocation, IIsCotItem> items = new LinkedHashMap<>();
    
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
}
