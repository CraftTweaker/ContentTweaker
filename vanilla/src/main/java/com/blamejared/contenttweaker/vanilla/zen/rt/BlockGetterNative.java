package com.blamejared.contenttweaker.vanilla.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@NativeTypeRegistration(value = BlockGetter.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".BlockGetter")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class BlockGetterNative {
    private BlockGetterNative() {}

    @ZenCodeType.Method("getBlockState")
    public static BlockState getBlockState(final BlockGetter $this, final BlockPos pos) {
        return $this.getBlockState(pos);
    }

    // TODO("Provide other methods maybe?")
}
