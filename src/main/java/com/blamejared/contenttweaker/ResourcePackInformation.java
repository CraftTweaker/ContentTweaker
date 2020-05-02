package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.contenttweaker.blocks.functions.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.impl.data.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.state.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class ResourcePackInformation {
    
    static final File RESOURCE_PACK_FOLDER = new File("the_loader/resourcepacks", ContentTweaker.MOD_ID);
    private static final byte[] NO_ICON = Base64.getDecoder()
            .decode("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAIAAACQkWg2AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAACgSURBVDhPhZLRCYAwDAWD3/lwCJGuILj/Kg7gAPE10dK0IR4Fo96BLZIwy3nKfUsOBGjMJKUI0U9jNrRSSK7rp+lsyFQfJY238UADEDaTDb4ADE1kgy4ArTmOuiYb+ADg9b5XFQuDt8FCAzjpdX1nDLgdeEOjffe21YWhPwOlC4Zdtv345gvCM4kaDULbmBrKbMM3eklso2v0985twxrmBzQGNQUhq3LZAAAAAElFTkSuQmCC");
    private static final Map<MCResourceLocation, IData> blockStateJsons = new HashMap<>();
    private static final Map<MCResourceLocation, IData> modelJsons = new HashMap<>();
    private static final Set<MCResourceLocation> textures = new HashSet<>();
    
    private ResourcePackInformation() {
    }
    
    public static void HandleBlockResourceInformation(CoTBlock block) {
        final MCResourceLocation mcResourceLocation = block.getMCResourceLocation();
        if(!mcResourceLocation.getNamespace().equals(ContentTweaker.MOD_ID)) {
            return;
        }
        
        final FunctionResourceLocationToIData blockToBlockStateMapping = block.getProperties().blockToBlockStateMapping;
        if(blockToBlockStateMapping == null) {
            return;
        }
        final IData blockStateContent = blockToBlockStateMapping.mapModel(mcResourceLocation);
        blockStateJsons.put(mcResourceLocation, blockStateContent);
        if(!(blockStateContent instanceof MapData && ((MapData) blockStateContent).contains("variants"))) {
            return;
        }
        
        final FunctionResourceLocationToIData modelToModelContentMapping = block.getProperties().modelToModelContentMapping;
        if(modelToModelContentMapping == null) {
            return;
        }
        
        final Map<String, IData> variants = ((MapData) blockStateContent).get("variants").asMap();
        IData variant = null;
        if(variants.size() == 1) {
            variant = new ArrayList<>(variants.values()).get(0);
        } else {
            final ImmutableMap<IProperty<?>, ?> values = block.getDefaultState().getValues();
            final StateContainer<Block, BlockState> stateContainer = block.getStateContainer();
            outer:
            for(String props : variants.keySet()) {
                final String[] property = props.split(",");
                for(String propKeyValue : property) {
                    final String[] split = propKeyValue.split("=", 2);
                    final String key = split[0];
                    final String value = split[1];
                    final IProperty<?> property1 = stateContainer.getProperty(key);
                    if(property1 == null) {
                        continue outer;
                    }
                    final Optional<?> optional = property1.parseValue(value);
                    if(!optional.isPresent() || optional.get() != values.get(property1)) {
                        continue outer;
                    }
                }
                variant = variants.get(props);
                break;
            }
        }
        
        final IData model = getMapMember(variant, "model");
        if(model instanceof StringData) {
            String s = model.getInternal().getString();
            final ResourceLocation location = ResourceLocation.tryCreate(s);
            if(location != null && location.getNamespace().equals(ContentTweaker.MOD_ID)) {
                String path = location.getPath();
                if(path.startsWith("block")) {
                    path = "item" + path.substring("block".length());
                }
                final MCResourceLocation itemModel = new MCResourceLocation(ContentTweaker.MOD_ID, path);
                modelJsons.put(itemModel, new MapData(Collections.singletonMap("parent", model)));
            }
        }
        
        
        variants.values()
                .stream()
                .filter(d -> d instanceof MapData && ((MapData) d).contains("model"))
                .map(d -> ResourceLocation.tryCreate(((MapData) d).get("model")
                        .getInternal()
                        .getString()))
                .filter(r -> r != null && r.getNamespace().equals(ContentTweaker.MOD_ID))
                .distinct()
                .map(MCResourceLocation::new)
                .forEach(r -> {
                    final IData modelContent = modelToModelContentMapping.mapModel(r);
                    if(modelContent == null) {
                        return;
                    }
                    modelJsons.put(r, modelContent);
                    if(modelContent instanceof MapData && ((MapData) modelContent).contains("textures")) {
                        handleTextureData(((MapData) modelContent).get("textures"));
                    }
                });
    }
    
    private static void handleTextureData(IData textureData) {
        if(textureData instanceof MapData) {
            for(IData d : textureData.asMap().values()) {
                ResourceLocation obj = ResourceLocation.tryCreate(d.getInternal().getString());
                if(Objects.nonNull(obj) && obj.getNamespace().equals(ContentTweaker.MOD_ID)) {
                    textures.add(new MCResourceLocation(obj));
                }
            }
            
        }
    }
    
    public static void HandleItemResourceInformation(CoTItem item) {
        final MCItemProperties mcItemProperties = item.getMcItemProperties();
        final FunctionResourceLocationToIData nameToModel = mcItemProperties.nameToModel;
        if(nameToModel == null) {
            return;
        }
        final MCResourceLocation mcResourceLocation = item.getMcResourceLocation();
        final IData modelJson = nameToModel.mapModel(mcResourceLocation);
        if(modelJson == null) {
            return;
        }
        modelJsons.put(new MCResourceLocation(mcResourceLocation.getNamespace(), "item/" + mcResourceLocation
                .getPath()), modelJson);
        final IData textures = getMapMember(modelJson, "textures");
        handleTextureData(textures);
    }
    
    private static IData getMapMember(IData data, String s) {
        if(!(data instanceof MapData)) {
            return null;
        }
        
        MapData mapData = (MapData) data;
        return mapData.contains(s) ? mapData.get(s) : null;
    }
    
    private static void writeAsset(MCResourceLocation location, String type, IData model) {
        final File modelFile = new File(RESOURCE_PACK_FOLDER, String.format("assets/%s/%s/%s.json", location
                .getNamespace(), type, location.getPath()));
        
        if(checkFileFolder(modelFile)) {
            try(final PrintWriter itemModelWriter = new PrintWriter(new FileWriter(modelFile))) {
                itemModelWriter.write(model.toJsonString());
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static boolean checkFileFolder(File modelFile) {
        try {
            Files.createDirectories(modelFile.toPath().getParent());
        } catch(IOException e) {
            throw new IllegalStateException("Could not create Directory " + modelFile.getParentFile(), e);
        }
        if(!modelFile.getParentFile().exists()) {
            System.out.println();
        }
        
        return !modelFile.exists();
    }
    
    public static void finish() {
        for(Map.Entry<MCResourceLocation, IData> entry : blockStateJsons.entrySet()) {
            final MCResourceLocation location = entry.getKey();
            writeAsset(location, "blockstates", entry.getValue());
        }
        
        for(Map.Entry<MCResourceLocation, IData> entry : modelJsons.entrySet()) {
            final MCResourceLocation location = entry.getKey();
            writeAsset(location, "models", entry.getValue());
        }
        
        for(MCResourceLocation location : textures) {
            final File modelFile = new File(RESOURCE_PACK_FOLDER, String.format("assets/%s/%s/%s.png", location
                    .getNamespace(), "textures", location.getPath()));
            
            if(checkFileFolder(modelFile)) {
                try(final FileOutputStream imageOutputStream = new FileOutputStream(modelFile)) {
                    imageOutputStream.write(NO_ICON);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
}
