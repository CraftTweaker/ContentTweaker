package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import net.minecraftforge.registries.*;

import java.io.*;

public class VanillaFactory {
    
    private static final CoTRegistry registry = new CoTRegistry();
    
    public static void registerBlock(IIsCoTBlock block) {
        registry.addBlock(block);
        registry.addItem(block.getItem());
    }
    
    public static void registerItem(IIsCotItem item) {
        registry.addItem(item);
    }
    
    public static void complete() {
        registry.getBlocksAsVanillaBlocks().forEach(ForgeRegistries.BLOCKS::register);
        registry.getItemsAsVanillaItems().forEach(ForgeRegistries.ITEMS::register);
        
        final File resourcePackDir = new File("the_loader/resourcepacks/contenttweaker");
        registry.getAssetResources()
                .filter(w -> ContentTweaker.MOD_ID.equals(w.getModId()))
                .forEach(w -> {
                    w.writeContentToFileRelativeTo(resourcePackDir);
                    w.onWrite();
                });
        
        
        final File dataPackDir = new File("the_loader/datapacks/contenttweaker");
        registry.getDataResources()
                .filter(w -> ContentTweaker.MOD_ID.equals(w.getModId()))
                .forEach(w -> {
                    w.writeContentToFileRelativeTo(dataPackDir);
                    w.onWrite();
                });
    }
}
