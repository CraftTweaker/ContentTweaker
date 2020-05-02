package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.google.gson.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.registries.*;

import java.io.*;
import java.util.*;

public class VanillaFactory {
    
    private static final List<CoTItem> items = new ArrayList<>();
    private static final List<CoTBlock> blocks = new ArrayList<>();
    
    public static boolean registerAllowed = false;
    
    public static void registerItem(CoTItem item, MCResourceLocation resourceLocation) {
        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                ForgeRegistries.ITEMS.register(item.setRegistryName(resourceLocation.getInternal()));
                items.add(item);
            }
            
            @Override
            public String describe() {
                return String.format("Registering item %s with resource location %s", item, resourceLocation
                        .getCommandString());
            }
            
            @Override
            public boolean validate(ILogger logger) {
                if(!registerAllowed) {
                    logger.error("Registering items too early or too late");
                    logger.error("Ignoring Registration for item " + item);
                    return false;
                }
                
                return true;
            }
            
            @Override
            public boolean shouldApplyOn(LogicalSide side) {
                return true;
            }
        });
    }
    
    public static void registerBlock(CoTBlock block, MCItemProperties mcItemProperties, MCResourceLocation resourceLocation) {
        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                ForgeRegistries.BLOCKS.register(block.setRegistryName(resourceLocation.getInternal()));
                
                final BlockItem blockItem = new BlockItem(block, mcItemProperties.createInternal());
                blockItem.addToBlockToItemMap(Item.BLOCK_TO_ITEM, blockItem);
                ForgeRegistries.ITEMS.register(blockItem.setRegistryName(resourceLocation.getInternal()));
                blocks.add(block);
            }
            
            @Override
            public String describe() {
                return String.format("Registering item %s with resource location %s", block, resourceLocation
                        .getCommandString());
            }
            
            @Override
            public boolean validate(ILogger logger) {
                if(!registerAllowed) {
                    logger.error("Registering items too early or too late");
                    logger.error("Ignoring Registration for item " + block);
                    return false;
                }
                
                return true;
            }
            
            @Override
            public boolean shouldApplyOn(LogicalSide side) {
                return true;
            }
        });
    }
    
    public static void writeResourcePack() {
        writeLangFile();
        
        for(CoTBlock block : blocks) {
            ResourcePackInformation.HandleBlockResourceInformation(block);
        }
        
        for(CoTItem item : items) {
            ResourcePackInformation.HandleItemResourceInformation(item);
        }
        
        ResourcePackInformation.finish();
    }
    
    private static void writeLangFile() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        final File langFile = new File(ResourcePackInformation.RESOURCE_PACK_FOLDER, "assets/contenttweaker/lang/en_us.json");
        final Map<String, String> langEntries;
        
        if(!langFile.exists()) {
            if(!langFile.getParentFile().exists() && !langFile.getParentFile().mkdirs()) {
                throw new IllegalStateException("Could not create Directory " + langFile.getParentFile());
            }
            langEntries = new HashMap<>();
        } else {
            Map<String, String> toSet;
            try(final FileReader reader = new FileReader(langFile)) {
                //noinspection unchecked
                toSet = (Map<String, String>) gson.fromJson(reader, Map.class);
            } catch(IOException e) {
                e.printStackTrace();
                toSet = new HashMap<>();
            }
            langEntries = toSet;
        }
        
        items.stream()
                .map(ForgeRegistryEntry::getRegistryName)
                .filter(Objects::nonNull)
                .map(location -> String.format("item.%s.%s", location.getNamespace(), location.getPath()))
                .forEach(translationKey -> langEntries.putIfAbsent(translationKey, translationKey));
        
        
        blocks.stream()
                .map(ForgeRegistryEntry::getRegistryName)
                .filter(Objects::nonNull)
                .map(location -> String.format("block.%s.%s", location.getNamespace(), location.getPath()))
                .forEach(translationKey -> langEntries.putIfAbsent(translationKey, translationKey));
        
        try(PrintWriter langFileWriter = new PrintWriter(new FileWriter(langFile, false))) {
            langFileWriter.println(gson.toJson(new TreeMap<>(langEntries), Map.class));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
}
