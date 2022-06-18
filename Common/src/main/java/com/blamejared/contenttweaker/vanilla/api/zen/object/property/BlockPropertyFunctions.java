package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.util.MaterialColorReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

public final class BlockPropertyFunctions {
    private BlockPropertyFunctions() {}

    @FunctionalInterface
    @ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.fun.LightLevelComputer")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public interface LightLevelComputer {
        int lightOf(final BlockState state);
    }

    @FunctionalInterface
    @ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.fun.MaterialColorFinder")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public interface MaterialColorFinder {
        MaterialColorReference colorOf(final BlockState state);
    }
}
