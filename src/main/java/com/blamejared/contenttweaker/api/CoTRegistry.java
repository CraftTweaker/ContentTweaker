package com.blamejared.contenttweaker.api;

import com.blamejared.contenttweaker.ContentTweaker;
import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import com.blamejared.contenttweaker.api.fluids.IIsCotFluid;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.api.resources.WriteableResource;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.extensions.IForgeBlock;
import net.minecraftforge.common.extensions.IForgeItem;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class CoTRegistry {
    
    private final Map<ResourceLocation, IIsCoTBlock> blocks = new LinkedHashMap<>();
    private final Map<ResourceLocation, IIsCotItem> items = new LinkedHashMap<>();
    private final Map<ResourceLocation, IIsCotFluid> fluids = new LinkedHashMap<>();

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

    public void addFluid(IIsCotFluid fluid) {
        if (fluids.containsKey(fluid.getRegistryName())) {
            CraftTweakerAPI.logError("Registering fluid '%s' a second time, overriding the first one. Make sure your calls to .register() use unique names!", fluid.getRegistryName());
        }
        fluids.put(fluid.getRegistryName(), fluid);
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

    public Collection<IIsCotFluid> getFluids() {
        return fluids.values();
    }

    public IIsCotItem getItem(ResourceLocation resourceLocation) {
        return Optional.ofNullable(items.get(resourceLocation)).orElseThrow(() -> new IllegalArgumentException("Could not find CoT item for " + resourceLocation.toString()));
    }

    public IIsCoTBlock getBlock(ResourceLocation resourceLocation) {
        return Optional.ofNullable(blocks.get(resourceLocation)).orElseThrow(() -> new IllegalArgumentException("Could not find CoT block for " + resourceLocation.toString()));
    }

    public IIsCotFluid getFluid(ResourceLocation resourceLocation) {
        return Optional.ofNullable(fluids.get(resourceLocation)).orElseThrow(() -> new IllegalArgumentException("Could not find CoT fluid for " + resourceLocation.toString()));
    }

    public IIsCotItem getItem(String location) {
        return getItem(new ResourceLocation(ContentTweaker.MOD_ID, location));
    }

    public IIsCoTBlock getBlock(String location) {
        return getBlock(new ResourceLocation(ContentTweaker.MOD_ID, location));
    }

    public IIsCotFluid getFluid(String location) {
        return getFluid(new ResourceLocation(ContentTweaker.MOD_ID, location));
    }
}
