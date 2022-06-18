package com.blamejared.contenttweaker.vanilla.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.util.Color;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.util.MaterialColorReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.level.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(MaterialColorMetaFactory.ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class MaterialColorMetaFactory {
    public static final String ZEN_NAME = ContentTweakerVanillaConstants.VANILLA_RT_PACKAGE + ".MaterialColorMetaFactory";

    private MaterialColorMetaFactory() {}

    @ZenCodeType.Method
    public static MaterialColorReference factory(final int id, final int color) {
        return MaterialColorReference.of(id, Color.packedRgb(color), MaterialColor::byId);
    }
}
