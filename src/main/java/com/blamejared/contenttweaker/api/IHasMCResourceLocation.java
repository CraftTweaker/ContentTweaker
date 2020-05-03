package com.blamejared.contenttweaker.api;

import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.util.*;

import javax.annotation.*;

@FunctionalInterface
public interface IHasMCResourceLocation {
    
    @Nonnull
    default MCResourceLocation getMCResourceLocation() {
        return new MCResourceLocation(getRegistryName());
    }
    
    // Will never be null, but this makes nullable warnings shut up ^^
    @Nullable
    ResourceLocation getRegistryName();
}
