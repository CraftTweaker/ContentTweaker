package com.blamejared.contenttweaker.blocks.types.stairs;

import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.crafttweaker.impl.util.*;

class WriteableResourceModelStairs extends WriteableResource {
    
    public WriteableResourceModelStairs(MCResourceLocation location, ModelType modelType, MCResourceLocation top, MCResourceLocation bottom, MCResourceLocation sides) {
        super(ResourceType.ASSETS, FileExtension.JSON, location.getNamespace(), location.getPath() + modelType
                .getSuffix(), "models", "block");
        
        this.withContent("{\n" + "    \"parent\": \"block/%s\",\n" + "    \"textures\": {\n" + "        \"bottom\": \"%s:block/%s\",\n" + "        \"top\": \"%s:block/%s\",\n" + "        \"side\": \"%s:block/%s\"\n" + "    }\n" + "}\n", modelType
                .getModelName(),
                bottom.getNamespace(), bottom.getPath(),
                top.getNamespace(), top.getPath(),
                sides.getNamespace(), sides.getPath());
    }
    
    public enum ModelType {
        INNER("_inner", "inner_stairs"), OUTER("_outer", "outer_stairs"), BASE("", "stairs");
        
        private final String suffix;
        private final String modelName;
    
        ModelType(String suffix, String modelName) {
            this.suffix = suffix;
            this.modelName = modelName;
        }
        
        public String getSuffix() {
            return suffix;
        }
    
        public String getModelName() {
            return modelName;
        }
    }
}
