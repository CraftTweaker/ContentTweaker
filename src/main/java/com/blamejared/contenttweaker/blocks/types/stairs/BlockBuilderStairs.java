package com.blamejared.contenttweaker.blocks.types.stairs;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

import java.util.function.*;

/**
 * A special Block Builder that allows you to create stairs.
 * <p>
 * Stairs will have not one but three textures that you will need to supply:
 * One for the top, one for the bottom and one for the sides.
 * By default these textures will use your blockname as name, suffixed by `_top`, `_bottom` or `_sides`.
 * As with most things here, sample images are generated for you by default, though.
 *
 * @docParam this new BlockBuilder().withType<BlockBuilderStairs>()
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.stairs.BlockBuilderStairs")
@Document("mods/contenttweaker/API/block/stairs/BlockBuilderStairs")
public class BlockBuilderStairs extends BlockTypeBuilder {
    
    private Function<ResourceLocation, ResourceLocation> top;
    private Function<ResourceLocation, ResourceLocation> bottom;
    private Function<ResourceLocation, ResourceLocation> sides;
    
    public BlockBuilderStairs(BlockBuilder blockBuilder) {
        super(blockBuilder);
        top = location -> new ResourceLocation(location.getNamespace(), location.getPath() + "_top");
        bottom = location -> new ResourceLocation(location.getNamespace(), location.getPath() + "_bottom");
        sides = location -> new ResourceLocation(location.getNamespace(), location.getPath() + "_sides");
    }
    
    
    public ResourceLocation getTop(ResourceLocation stairsName) {
        return top.apply(stairsName);
    }
    
    public ResourceLocation getBottom(ResourceLocation stairsName) {
        return bottom.apply(stairsName);
    }
    
    public ResourceLocation getSides(ResourceLocation stairsName) {
        return sides.apply(stairsName);
    }
    
    /**
     * Allows you to override the path of the texture that the top side should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     *
     * @param topTexture The texture to be used for the top side
     * @return This builder, used for method chaining
     * @docParam topTexture <resource:contenttweaker:my_awesome_stairs_top>
     */
    @ZenCodeType.Method
    public BlockBuilderStairs withTopTexture(ResourceLocation topTexture) {
        this.top = ignored -> topTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the bottom side should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     *
     * @param bottomTexture The texture to be used for the bottom side
     * @return This builder, used for method chaining
     * @docParam topTexture <resource:contenttweaker:my_awesome_stairs_bottom>
     */
    @ZenCodeType.Method
    public BlockBuilderStairs withBottomTexture(ResourceLocation bottomTexture) {
        this.bottom = ignored -> bottomTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the sides should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     *
     * @param sidesTexture The texture to be used for the sides
     * @return This builder, used for method chaining
     * @docParam topTexture <resource:contenttweaker:my_awesome_stairs_sides>
     */
    @ZenCodeType.Method
    public BlockBuilderStairs withSidesTexture(ResourceLocation sidesTexture) {
        this.sides = ignored -> sidesTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the top side should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the end texture for it.
     *
     * @param topTexture The function to use
     * @return This builder, used for method chaining
     * @docParam endTexture (blockName as ResourceLocation) => new ResourceLocation(blockName.namespace, blockName.path + "_top")
     */
    @ZenCodeType.Method
    public BlockBuilderStairs withTopTexture(Function<ResourceLocation, ResourceLocation> topTexture) {
        this.top = topTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the bottom side should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the end texture for it.
     *
     * @param bottomTexture The function to use
     * @return This builder, used for method chaining
     * @docParam bottomTexture (blockName as ResourceLocation) => new ResourceLocation(blockName.namespace, blockName.path + "_bottom")
     */
    @ZenCodeType.Method
    public BlockBuilderStairs withBottomTexture(Function<ResourceLocation, ResourceLocation> bottomTexture) {
        this.bottom = bottomTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the sides should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the end texture for it.
     *
     * @param sidesTexture The function to use
     * @return This builder, used for method chaining
     * @docParam sidesTexture (blockName as ResourceLocation) => new ResourceLocation(blockName.namespace, blockName.path + "_sides")
     */
    @ZenCodeType.Method
    public BlockBuilderStairs withSidesTexture(Function<ResourceLocation, ResourceLocation> sidesTexture) {
        this.sides = sidesTexture;
        return this;
    }
    
    @Override
    public void build(ResourceLocation location) {
        CoTStairsBlock cotStairs = new CoTStairsBlock(this, location);
        if (blockBuilder.allowTinted) {
            cotStairs.setAllowTinted();
        }
        VanillaFactory.queueBlockForRegistration(cotStairs);
    }
}
