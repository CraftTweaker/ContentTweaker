package com.blamejared.contenttweaker.blocks.types.stairs;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.util.*;

class WriteableResourceModelStairs extends WriteableResourceTemplate {
    
    public WriteableResourceModelStairs(MCResourceLocation location, ModelType modelType, MCResourceLocation top, MCResourceLocation bottom, MCResourceLocation sides) {
        super(ResourceType.ASSETS, location.getNamespace(), location.getPath() + modelType.getSuffix(), "models", "block");
        
        this.withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/block/block_stairs"))
                .setLocationProperty(top, "TOP")
                .setLocationProperty(bottom, "BOTTOM")
                .setLocationProperty(sides, "SIDE")
                .setProperty("STAIRS_PARENT", modelType.modelName);
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
