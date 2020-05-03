package com.blamejared.contenttweaker.api;

import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraftforge.common.extensions.*;

import java.util.*;
import java.util.stream.*;

public class CoTRegistry {
    
    private final Map<MCResourceLocation, IIsCoTBlock> blocks = new HashMap<>();
    private final Map<MCResourceLocation, IIsCotItem> items = new HashMap<>();
    
    public void addBlock(IIsCoTBlock block) {
        blocks.put(block.getMCResourceLocation(), block);
    }
    
    public void addItem(IIsCotItem item) {
        items.put(item.getMCResourceLocation(), item);
    }
    
    public Stream<Block> getBlocksAsVanillaBlocks() {
        return blocks.values().stream().map(IForgeBlock::getBlock);
    }
    
    public Collection<IIsCoTBlock> getBlocks() {
        return blocks.values();
    }
    
    public Stream<WriteableResource> getAssetResources() {
        return Stream.concat(getBlocks().stream(), getItems().stream())
                .map(o -> ((IHasResourcesToWrite) o))
                .flatMap(iHasResourcesToWrite -> iHasResourcesToWrite.getResourcePackResources().stream());
    }
    
    public Stream<WriteableResource> getDataResources() {
        return Stream.concat(getBlocks().stream(), getItems().stream())
                .flatMap(iHasResourcesToWrite -> iHasResourcesToWrite.getDataPackResources().stream());
    }
    
    public Collection<IIsCotItem> getItems() {
        return items.values();
    }
    
    public Stream<Item> getItemsAsVanillaItems() {
        return getItems().stream().map(IForgeItem::getItem);
    }
}
