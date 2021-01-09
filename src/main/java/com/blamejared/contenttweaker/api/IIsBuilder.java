package com.blamejared.contenttweaker.api;

import com.blamejared.contenttweaker.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

/**
 * Denotes anything that is a builder.
 * What did you expect?
 */
@ZenRegister
@Document("mods/contenttweaker/API/api/IIsBuilder")
@ZenCodeType.Name("mods.contenttweaker.api.IIsBuilder")
public interface IIsBuilder {
    
    /**
     * Instructs CoT to actually build whatever this builder is supposed to be building.
     *
     * @param resourceLocation The resource path to give this block
     * @docParam resourceLocation "my_awesome_block"
     */
    @ZenCodeType.Method
    default void build(String resourceLocation) {
        build(new ResourceLocation(ContentTweaker.MOD_ID, resourceLocation));
    }
    
    /**
     * Instructs CoT to actually build whatever this builder is supposed to be building.
     * This method is not recommended to be used, use the one that only accepts the path
     *
     * @param location The name to give this block
     */
    void build(ResourceLocation location);
}
