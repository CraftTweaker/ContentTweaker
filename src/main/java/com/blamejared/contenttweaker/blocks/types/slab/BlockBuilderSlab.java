package com.blamejared.contenttweaker.blocks.types.slab;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

import java.util.function.*;

/**
 * A special builder that allows you to create slabs.
 *
 * <p>
 * By default, this has 3 textures, one for the top, bottom and the sides.
 * As with most things here, sample images are generated for you by default, though.
 *
 * @docParam this new BlockBuilder().withType<BlockBuilderSlab>()
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.slab.BlockBuilderSlab")
@Document("mods/contenttweaker/API/block/slab/BlockBuilderSlab")
public class BlockBuilderSlab extends BlockTypeBuilder {
    
    private Function<ResourceLocation, ResourceLocation> top;
    private Function<ResourceLocation, ResourceLocation> bottom;
    private Function<ResourceLocation, ResourceLocation> sides;
    
    
    public BlockBuilderSlab(BlockBuilder blockBuilder) {
        super(blockBuilder);
        top = location -> new ResourceLocation(location.getNamespace(), location.getPath() + "_top");
        bottom = location -> new ResourceLocation(location.getNamespace(), location.getPath() + "_bottom");
        sides = location -> new ResourceLocation(location.getNamespace(), location.getPath() + "_sides");
    }
    
    public ResourceLocation getTop(ResourceLocation location) {
        return top.apply(location);
    }
    
    public ResourceLocation getBottom(ResourceLocation location) {
        return bottom.apply(location);
    }
    
    public ResourceLocation getSides(ResourceLocation location) {
        return sides.apply(location);
    }
    
    @Override
    public void build(ResourceLocation location) {
        CoTBlockSlab blockSlab = new CoTBlockSlab(this, location);
        VanillaFactory.queueBlockForRegistration(blockSlab);
    }
    
    /**
     * Allows you to override the path of the texture that the sides (everything but top/bottom) should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     *
     * @param sidesTexture The texture to be used for the sides.
     * @return This builder, used for method chaining
     * @docParam sidesTexture <resource:contenttweaker:my_awesome_slab_side>
     */
    @ZenCodeType.Method
    public BlockBuilderSlab withSideTexture(ResourceLocation sidesTexture) {
        this.sides = ignored -> sidesTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the sides (everything but top/bottom) should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the sides texture for it.
     *
     * @param sidesTexture The function to use
     * @return This builder, used for method chaining
     * @docParam sidesTexture (blockName as ResourceLocation) => new ResourceLocation(blockName.namespace, blockName.path + "_sides")
     */
    @ZenCodeType.Method
    public BlockBuilderSlab withSideTexture(Function<ResourceLocation, ResourceLocation> sidesTexture) {
        this.sides = sidesTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the top should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     *
     * @param topTexture The texture to be used for the sides.
     * @return This builder, used for method chaining
     * @docParam topTexture <resource:contenttweaker:my_awesome_slab_top>
     */
    @ZenCodeType.Method
    public BlockBuilderSlab withTopTexture(ResourceLocation topTexture) {
        this.top = ignored -> topTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the top should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the top texture for it.
     *
     * @param topTexture The function to use
     * @return This builder, used for method chaining
     * @docParam topTexture (blockName as ResourceLocation) => new ResourceLocation(blockName.namespace, blockName.path + "_top")
     */
    @ZenCodeType.Method
    public BlockBuilderSlab withTopTexture(Function<ResourceLocation, ResourceLocation> topTexture) {
        this.top = topTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the bottom should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     *
     * @param bottomTexture The texture to be used for the sides.
     * @return This builder, used for method chaining
     * @docParam bottomTexture <resource:contenttweaker:my_awesome_slab_bottom>
     */
    @ZenCodeType.Method
    public BlockBuilderSlab withBottomTexture(ResourceLocation bottomTexture) {
        this.bottom = ignored -> bottomTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the bottom should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the bottom texture for it.
     *
     * @param bottomTexture The function to use
     * @return This builder, used for method chaining
     * @docParam bottomTexture (blockName as ResourceLocation) => new ResourceLocation(blockName.namespace, blockName.path + "_top")
     */
    @ZenCodeType.Method
    public BlockBuilderSlab withBottomTexture(Function<ResourceLocation, ResourceLocation> bottomTexture) {
        this.bottom = bottomTexture;
        return this;
    }
    
    
}
