package com.blamejared.contenttweaker.blocks.types.custom;

import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.impl.data.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.util.*;

import java.util.*;
import java.util.stream.*;

class WriteableResourceBlockStateCustom extends WriteableResource {
    
    private final Collection<WriteableResourceModelContentCustom> referencedModels;
    
    public WriteableResourceBlockStateCustom(CoTBlockCustom blockCustom) {
        super(ResourceType.ASSETS, FileExtension.JSON, blockCustom.getMCResourceLocation(), "blockstates");
        this.referencedModels = new ArrayList<>();
    
        final byte[] content = createContentSupplier(blockCustom);
        this.withContent(content);
    }
    
    public byte[] createContentSupplier(CoTBlockCustom block) {
        final MCResourceLocation location = block.getMCResourceLocation();
        
        final BuilderBlockCustom builder = block.getBuilder();
        final Set<MCBlockStateProperty> properties = builder.getBlockStatePropertiesForBlockStateJson();
        final BlockStateToModelMapping modelMapping = builder.getBlockStateToModelMapping();
        
        final MapData allValues = HelpersThatNeedToBeRefactored.getAllValues(properties, modelMapping, location);
        
        
        final Set<ResourceLocation> models = new HashSet<>();
        for(Map.Entry<String, IData> variant : allValues.get("variants").asMap().entrySet()) {
            final IData variantValue = variant.getValue();
            if(!(variantValue instanceof MapData)) {
                continue;
            }
    
            final Map<String, IData> variantMap = variantValue.asMap();
            if(!variantMap.containsKey("model")) {
                continue;
            }
    
            final String string = variantMap.get("model").getInternal().getString();
            final ResourceLocation modelLocation = ResourceLocation.tryCreate(string);
            if(modelLocation != null) {
                models.add(modelLocation);
            }
        }
        
        for(ResourceLocation model : models) {
            final WriteableResourceModelContentCustom modelContent = new WriteableResourceModelContentCustom(new MCResourceLocation(model), block);
            this.referencedModels.add(modelContent);
        }
        
        return allValues.toJsonString()
                .replaceAll("\"true\"", "true")
                .replaceAll("\"false\"", "false")
                .getBytes();
        
    }
    
    public Stream<WriteableResource> getReferencedModelsAndTextures() {
        return this.referencedModels.stream()
                .flatMap(w -> Stream.concat(Stream.of(w), w.getReferencedTextures()));
    }
}
