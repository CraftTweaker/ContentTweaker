package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.contenttweaker.blocks.functions.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.impl.data.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.google.gson.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.registries.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class VanillaFactory {
    
    private static final byte[] NO_ICON = Base64.getDecoder()
            .decode("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAIAAACQkWg2AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAACgSURBVDhPhZLRCYAwDAWD3/lwCJGuILj/Kg7gAPE10dK0IR4Fo96BLZIwy3nKfUsOBGjMJKUI0U9jNrRSSK7rp+lsyFQfJY238UADEDaTDb4ADE1kgy4ArTmOuiYb+ADg9b5XFQuDt8FCAzjpdX1nDLgdeEOjffe21YWhPwOlC4Zdtv345gvCM4kaDULbmBrKbMM3eklso2v0985twxrmBzQGNQUhq3LZAAAAAElFTkSuQmCC");
    
    private static final File RESOURCE_PACK_FOLDER = new File("the_loader/resourcepacks", ContentTweaker.MOD_ID);
    private static final List<CoTItem> items = new ArrayList<>();
    private static final List<CoTBlock> blocks = new ArrayList<>();
    private static final Map<ResourceLocation, IData> blockModels = new HashMap<>();
    private static final Map<ResourceLocation, IData> itemModels = new HashMap<>();
    
    public static boolean registerAllowed = false;
    public static boolean writeTextures = true;
    public static boolean writeModels = true;
    
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
    
    static void createResourcePackFolders() {
        final boolean the_loader = ModList.get().isLoaded("theloader");
        if(!the_loader) {
            //Yes this should be caught by the toml but why not?
            throw new IllegalStateException("CoT requires TheLoader mod to be present!");
        }
        if(!RESOURCE_PACK_FOLDER.exists() && !RESOURCE_PACK_FOLDER.mkdirs()) {
            throw new IllegalStateException("Could not create Folders!");
        }
        
        final File mcMeta = new File(RESOURCE_PACK_FOLDER, "pack.mcmeta");
        if(!mcMeta.exists()) {
            try(final PrintWriter mcMetaWriter = new PrintWriter(new FileWriter(mcMeta, false))) {
                mcMetaWriter.println("{");
                mcMetaWriter.println("  \"pack\": {");
                mcMetaWriter.println("      \"pack_format\": 4,");
                mcMetaWriter.println("      \"description\": \"" + ContentTweaker.NAME + " Textures\"");
                mcMetaWriter.println("   }");
                mcMetaWriter.println("}");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        if(Stream.of("assets/contenttweaker/textures/item", "assets/contenttweaker/textures/block", "assets/contenttweaker/models/item", "assets/contenttweaker/models/block", "assets/contenttweaker/lang")
                .map(name -> new File(RESOURCE_PACK_FOLDER, name))
                .filter(f -> !f.exists())
                .anyMatch(f -> !f.mkdirs())) {
            throw new IllegalStateException("Could not create Folders!");
        }
        
        final File langFile = new File(RESOURCE_PACK_FOLDER, "assets/contenttweaker/lang/en_us.json");
        if(!langFile.exists()) {
            try(final PrintWriter langFileWriter = new PrintWriter(new FileWriter(langFile))) {
                langFileWriter.println("{");
                langFileWriter.println("}");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void writeResourcePack() {
        writeLangFile();
        
        if(writeModels) {
            items.stream()
                    .map(ForgeRegistryEntry::getRegistryName)
                    .filter(Objects::nonNull)
                    .forEach(VanillaFactory::WriteItemModel);
            
            blocks.forEach(VanillaFactory::WriteBlockModel);
        }
        
        if(writeTextures) {
            items.stream()
                    .map(ForgeRegistryEntry::getRegistryName)
                    .filter(Objects::nonNull)
                    .map(location -> new File(RESOURCE_PACK_FOLDER, String.format("assets/%s/textures/item/%s.png", location
                            .getNamespace(), location.getPath())))
                    .filter(file -> !file.exists())
                    .forEach(VanillaFactory::WriteDefaultTexture);
            
            blocks.stream()
                    .map(ForgeRegistryEntry::getRegistryName)
                    .filter(Objects::nonNull)
                    .map(location -> new File(RESOURCE_PACK_FOLDER, String.format("assets/%s/textures/block/%s.png", location
                            .getNamespace(), location.getPath())))
                    .filter(file -> !file.exists())
                    .forEach(VanillaFactory::WriteDefaultTexture);
        }
    }
    
    private static void WriteDefaultTexture(File textureFile) {
        if(!textureFile.getParentFile().exists() && !textureFile.getParentFile().mkdirs()) {
            throw new IllegalStateException("Could not create Directory " + textureFile.getParentFile());
        }
        
        if(!textureFile.exists()) {
            try(final FileOutputStream textureFileStream = new FileOutputStream(textureFile)) {
                textureFileStream.write(NO_ICON);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void writeLangFile() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        final File langFile = new File(RESOURCE_PACK_FOLDER, "assets/contenttweaker/lang/en_us.json");
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
                toSet = Collections.emptyMap();
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
    
    private static void WriteItemModel(ResourceLocation location) {
        final File itemModel = new File(RESOURCE_PACK_FOLDER, String.format("assets/%s/models/item/%s.json", location
                .getNamespace(), location.getPath()));
        
        if(!itemModel.exists() && !itemModel.getParentFile().exists() && !itemModel.getParentFile()
                .mkdirs()) {
            throw new IllegalStateException("Could not create Directory " + itemModel.getParentFile());
        }
        
        if(!itemModel.exists()) {
            try(final PrintWriter printWriter = new PrintWriter(new FileWriter(itemModel))) {
                printWriter.println("{");
                printWriter.println("  \"parent\": \"item/generated\",");
                printWriter.println("  \"textures\": {");
                printWriter.printf("    \"layer0\": \"%s:item/%s\"%n", location.getNamespace(), location
                        .getPath());
                printWriter.println("  }");
                printWriter.println("}");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void WriteBlockModel(CoTBlock block) {
        final MCResourceLocation mcResourceLocation = block.getMCResourceLocation();
        final ResourceLocation location = mcResourceLocation.getInternal();
        if(location == null) {
            return;
        }
        
        writeBlockState(block, mcResourceLocation);
    }
    
    private static void writeAsset(ResourceLocation location, IData model, String type) {
        final File itemModel = new File(RESOURCE_PACK_FOLDER, String.format("assets/%s/%s/%s.json", location
                .getNamespace(), type, location.getPath()));
        if(itemModel.exists() && !itemModel.getParentFile().exists() && !itemModel.getParentFile()
                .mkdirs()) {
            throw new IllegalStateException("Could not create Directory " + itemModel.getParentFile());
        }
        
        try(final PrintWriter itemModelWriter = new PrintWriter(new FileWriter(itemModel))) {
            itemModelWriter.write(model.toJsonString());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void writeBlockState(CoTBlock block, MCResourceLocation mcResourceLocation) {
        final String path = mcResourceLocation.getPath();
        final String namespace = mcResourceLocation.getNamespace();
        final File blockState = new File(RESOURCE_PACK_FOLDER, String.format("assets/%s/models/block/%s.json", namespace, path));
        if(!blockState.getParentFile().exists() && !blockState.getParentFile().mkdirs()) {
            throw new IllegalStateException("Could not create Directory " + blockState.getParentFile());
        }
        
        
        final MCBlockProperties properties = block.getProperties();
        final BlockStateToModelMapping blockStateToModelMapping = properties.blockStateToModelMapping;
        if(blockStateToModelMapping == null) {
            return;
        }
        
        if(!blockState.exists()) {
            final StringJoiner stringJoiner = new StringJoiner(",\n");
            final Map<String, IData> allValues = HelpersThatNeedToBeRefactored.getAllValues(properties, mcResourceLocation);
            allValues.forEach((s, iData) -> stringJoiner.add(String.format("        \"%s\": %s", s, iData
                    .toJsonString())));
            
            allValues.values()
                    .stream()
                    .filter(d -> d instanceof MapData)
                    .map(d -> ((MapData) d).get("model"))
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .distinct()
                    .map(ResourceLocation::tryCreate)
                    .filter(obj -> obj != null && blockModels.containsKey(obj))
                    .forEach(location -> writeAsset(location, blockModels.get(location)));
            
            
            try(final PrintWriter modelWriter = new PrintWriter(new FileWriter(blockState))) {
                modelWriter.println("{");
                modelWriter.println("    \"variants\": {");
                modelWriter.println(stringJoiner.toString());
                modelWriter.println("    }");
                modelWriter.println("}");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
