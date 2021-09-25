package com.blamejared.contenttweaker.blocks.types.falling;

import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.blocks.BlockTypeBuilder;
import com.blamejared.contenttweaker.api.functions.IFallingBlockDustColorSupplier;
import com.blamejared.contenttweaker.blocks.BlockBuilder;
import com.blamejared.contenttweaker.blocks.render.BlockRenderType;
import com.blamejared.contenttweaker.blocks.types.basic.CoTBlockBasic;
import com.blamejared.contenttweaker.blocks.types.slab.BlockBuilderSlab;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A special builder that allows you to create falling blocks.
 *
 * @docParam this new BlockBuilder().withType<BlockBuilderFalling>()
 */
@ZenRegister
@Document("mods/contenttweaker/API/block/basic/BlockBuilderFalling")
@ZenCodeType.Name("mods.contenttweaker.block.basic.BlockBuilderFalling")
public class BlockBuilderFalling extends BlockTypeBuilder {
    
    private IFallingBlockDustColorSupplier dustfunc = (thisBlock, state, reader, pos) -> 0xff000000;
    public BlockBuilderFalling(BlockBuilder builder) {
        super(builder);
    }
    
    @Override
    public void build(ResourceLocation location) {
        CoTBlockFalling blockFalling = new CoTBlockFalling(blockBuilder.getBlockProperties(), blockBuilder.getItemProperties(), location, dustfunc);
        BlockRenderType renderType = this.blockBuilder.getRenderType();
        VanillaFactory.queueBlockForRegistration(blockFalling, renderType);
    }
    
    
    /**
     * Allows you to override the dust color of this falling block.
     * The dust color is used when the block is floating with nothing beneath it.
     *
     * @param dustFunc The function that provides the dust color.
     * @return This builder, used for method chaining
     * @docParam dustFunc (thisBlock, state, reader, pos) => 0xFF55FF
     */
    @ZenCodeType.Method
    public BlockBuilderFalling withDustColor(IFallingBlockDustColorSupplier dustFunc) {
        this.dustfunc = dustFunc;
        return this;
    }
}
