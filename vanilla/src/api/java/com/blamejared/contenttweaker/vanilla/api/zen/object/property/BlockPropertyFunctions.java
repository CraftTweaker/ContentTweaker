package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.MapColorReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

public final class BlockPropertyFunctions {
    @FunctionalInterface
    @ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.fun.LightLevelComputer")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public interface LightLevelComputer {
        int lightOf(final BlockState state);
    }

    @FunctionalInterface
    @ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.fun.MapColorComputer")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public interface MapColorComputer {
        MapColorReference mapColorOf(final BlockState state);
    }

    @FunctionalInterface
    @ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.fun.SimpleStatePredicate")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public interface SimpleStatePredicate {
        boolean test(final BlockState state, final BlockGetter getter, final BlockPos pos);
    }

    @FunctionalInterface
    @ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.fun.ValidSpawnPredicate")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public interface ValidSpawnPredicate {
        // TODO("Verify reference usage")
        boolean isValidSpawn(final BlockState state, final BlockGetter getter, final BlockPos pos, final Reference<EntityType<?>> entityTypeReference);
    }

    private BlockPropertyFunctions() {}
}
