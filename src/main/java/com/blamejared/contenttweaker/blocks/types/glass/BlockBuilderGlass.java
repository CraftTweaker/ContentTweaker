package com.blamejared.contenttweaker.blocks.types.glass;

import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.blocks.BlockTypeBuilder;
import com.blamejared.contenttweaker.blocks.BlockBuilder;
import com.blamejared.contenttweaker.blocks.render.BlockRenderType;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A special builder that allows you to create glass blocks.
 *
 * @docParam this new BlockBuilder().withType<BlockBuilderGlass>()
 */
@ZenRegister
@Document("mods/contenttweaker/API/block/basic/BlockBuilderGlass")
@ZenCodeType.Name("mods.contenttweaker.block.basic.BlockBuilderGlass")
public class BlockBuilderGlass extends BlockTypeBuilder {
    
    public BlockBuilderGlass(BlockBuilder blockBuilder) {
        super(blockBuilder);
    }
    
    @Override
    public void build(ResourceLocation location) {
        CoTBlockGlass blockBasic = new CoTBlockGlass(blockBuilder.getBlockProperties(), blockBuilder.getItemProperties(), location);
        BlockRenderType renderType = this.blockBuilder.getRenderType();
        VanillaFactory.queueBlockForRegistration(blockBasic, renderType);
    }
    
}
