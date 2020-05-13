package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.crafttweaker.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.registries.*;

import java.io.*;
import java.nio.file.*;

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
    
        writeResourcePack();
        writeData();
    }
    
    private static void writeResourcePack() {
        final File resourcePackDir;
        final ModList modList = ModList.get();
        if(modList.isLoaded("theloader")){
            resourcePackDir = new File("the_loader/resourcepacks/contenttweaker");
        } else if (modList.isLoaded("openloader")){
            resourcePackDir = new File("openloader/resources/contenttweaker");
        } else {
            CraftTweakerAPI.logInfo("Could not find resource loader mod, no resource pack will be generated!");
            return;
        }
        
        if(!resourcePackDir.exists()){
            try {
                Files.createDirectories(resourcePackDir.toPath());
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }
        
        if(!new File(resourcePackDir, "pack.mcmeta").exists()) {
            try(final PrintWriter writer = new PrintWriter(new FileWriter(new File(resourcePackDir, "pack.mcmeta")))) {
                writer.write("{\n" + "   \"pack\": {\n" + "      \"pack_format\": 5,\n" + "      \"description\": \"ContentTweaker resources\"\n" + "   }\n" + "}");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        
        registry.getAssetResources()
                .filter(w -> ContentTweaker.MOD_ID.equals(w.getModId()))
                .forEach(w -> {
                    w.writeContentToFileRelativeTo(resourcePackDir);
                    w.onWrite();
                });
    }
    
    private static void writeData() {
        final File dataPackDir;
        final File resourcePackDir;
        final ModList modList = ModList.get();
        if(modList.isLoaded("theloader")){
            dataPackDir = new File("the_loader/datapacks/contenttweaker");
        } else if (modList.isLoaded("openloader")){
            dataPackDir = new File("openloader/data/contenttweaker");
        } else {
            CraftTweakerAPI.logInfo("Could not find resource loader mod, no data pack will be generated!");
            return;
        }
        
        
        registry.getDataResources()
                .filter(w -> ContentTweaker.MOD_ID.equals(w.getModId()))
                .forEach(w -> {
                    w.writeContentToFileRelativeTo(dataPackDir);
                    w.onWrite();
                });
    }
}
