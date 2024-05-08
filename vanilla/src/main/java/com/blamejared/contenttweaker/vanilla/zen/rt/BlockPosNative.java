package com.blamejared.contenttweaker.vanilla.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import org.openzen.zencode.java.ZenCodeType;

@NativeTypeRegistration(value = BlockPos.class, zenCodeName = ContentTweakerVanillaConstants.VANILLA_NATIVE_PACKAGE + ".BlockPos")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class BlockPosNative {
    private BlockPosNative() {}

    @ZenCodeType.Getter("x")
    public static int x(final BlockPos $this) {
        return $this.getX();
    }

    @ZenCodeType.Getter("y")
    public static int y(final BlockPos $this) {
        return $this.getY();
    }

    @ZenCodeType.Getter("z")
    public static int z(final BlockPos $this) {
        return $this.getZ();
    }

    // TODO("Consider exposing other methods, but this is all you need to be able to use this effectively")
}
