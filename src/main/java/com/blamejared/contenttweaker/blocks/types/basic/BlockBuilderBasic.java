package com.blamejared.contenttweaker.blocks.types.basic;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

/**
 * This builder builds the block type that is used by default in {@link BlockBuilder#build}
 * This builder does not offer any special properties to set, you can only build it.
 *
 * @docParam this new BlockBuilder().withType<ItemBuilderBasic>()
 */
@ZenRegister
@Document("mods/contenttweaker/API/block/basic/BlockBuilderBasic")
@ZenCodeType.Name("mods.contenttweaker.block.basic.BlockBuilderBasic")
public class BlockBuilderBasic extends BlockTypeBuilder {
    
    
    public BlockBuilderBasic(BlockBuilder builder) {
        super(builder);
    }
    
    @Override
    public void build(ResourceLocation location) {
        CoTBlockBasic blockBasic = new CoTBlockBasic(blockBuilder.getBlockProperties(), blockBuilder.getItemProperties(), location);
        VanillaFactory.queueBlockForRegistration(blockBasic);
    }
}
