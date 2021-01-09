package com.blamejared.contenttweaker.api;

import net.minecraft.util.*;

import javax.annotation.*;
import java.util.*;

@FunctionalInterface
public interface IHasResourceLocation {
    
    // Will never be null, but this makes nullable warnings shut up ^^
    @Nullable
    ResourceLocation getRegistryName();
    
    @Nonnull
    default ResourceLocation getRegistryNameNonNull() {
        return Objects.requireNonNull(getRegistryName());
    }
}
