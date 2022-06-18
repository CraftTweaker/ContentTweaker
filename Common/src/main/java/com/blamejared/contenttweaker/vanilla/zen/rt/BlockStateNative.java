package com.blamejared.contenttweaker.vanilla.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.block.state.BlockState;

@NativeTypeRegistration(value = BlockState.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_RT_PACKAGE + ".BlockState")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class BlockStateNative {
    private BlockStateNative() {}

    // TODO("Property querying, I guess?")
}
