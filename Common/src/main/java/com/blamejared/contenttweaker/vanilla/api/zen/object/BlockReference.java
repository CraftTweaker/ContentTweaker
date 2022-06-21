package com.blamejared.contenttweaker.vanilla.api.zen.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".BlockReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class BlockReference extends Reference<Block> {
    private BlockReference(final ResourceLocation id) {
        super(VanillaObjectTypes.BLOCK, id);
    }

    @ZenCodeType.Method("of")
    public static BlockReference of(final ResourceLocation id) {
        return new BlockReference(id);
    }
}
