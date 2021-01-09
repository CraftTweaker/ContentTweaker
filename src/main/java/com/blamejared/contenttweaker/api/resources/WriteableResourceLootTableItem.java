package com.blamejared.contenttweaker.api.resources;

import com.blamejared.contenttweaker.*;
import net.minecraft.util.*;

public class WriteableResourceLootTableItem extends WriteableResourceTemplate {
    
    public WriteableResourceLootTableItem(ResourceLocation block) {
        this(block, "block_basic");
    }
    
    public WriteableResourceLootTableItem(ResourceLocation block, String name) {
        super(ResourceType.DATA, block, "loot_tables", "blocks");
        
        final ResourceLocation location = new ResourceLocation(ContentTweaker.MOD_ID, "loot_tables/blocks/" + name);
        this.withTemplate(ResourceType.DATA, location).setLocationProperty(block);
    }
}
