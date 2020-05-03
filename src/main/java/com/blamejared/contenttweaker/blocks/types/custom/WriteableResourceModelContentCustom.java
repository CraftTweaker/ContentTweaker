package com.blamejared.contenttweaker.blocks.types.custom;

import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.impl.data.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.util.*;

import java.util.*;
import java.util.stream.*;

final class WriteableResourceModelContentCustom extends WriteableResource {
    
    private final Collection<WriteableResource> referencedTextures;
    
    public WriteableResourceModelContentCustom(MCResourceLocation modelLocation, CoTBlockCustom blockCustom) {
        super(ResourceType.ASSETS, FileExtension.JSON, modelLocation, "models");
        referencedTextures = new ArrayList<>();
        
        final byte[] content = createContent(modelLocation, blockCustom);
        this.withContent(content);
    }
    
    public Stream<WriteableResource> getReferencedTextures() {
        return referencedTextures.stream();
    }
    
    private byte[] createContent(MCResourceLocation modelLocation, CoTBlockCustom blockCustom) {
        final BuilderBlockCustom builder = blockCustom.getBuilder();
        final FunctionResourceLocationToIData modelNameToContentMapping = builder.getModelNameToModelContentMapping();
        
        final IData modelContent = modelNameToContentMapping.mapModel(modelLocation);
        if(modelContent instanceof MapData) {
            handleModelContent((MapData) modelContent);
        }
        
        
        return modelContent.toJsonString()
                .replaceAll("\"true\"", "true")
                .replaceAll("\"false\"", "false")
                .getBytes();
    }
    
    private void handleModelContent(MapData modelContent) {
        if(!modelContent.contains("textures")) {
            return;
        }
        
        final Set<ResourceLocation> textures = new HashSet<>();
        for(IData texture : modelContent.get("textures").asMap().values()) {
            final ResourceLocation resourceLocation = ResourceLocation.tryCreate(texture.getInternal()
                    .getString());
            if(resourceLocation != null) {
                textures.add(resourceLocation);
            }
        }
        
        for(ResourceLocation texture : textures) {
            referencedTextures.add(new WriteableResourceImage(new MCResourceLocation(texture)));
        }
    }
}
