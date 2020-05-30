package com.blamejared.contenttweaker.blocks.types.stairs;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
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
    
    private Function<MCResourceLocation, MCResourceLocation> top;
    private Function<MCResourceLocation, MCResourceLocation> bottom;
    private Function<MCResourceLocation, MCResourceLocation> sides;
    
    public BlockBuilderStairs(BlockBuilder blockBuilder) {
        super(blockBuilder);
        top = location -> new MCResourceLocation(location.getNamespace(), location.getPath() + "_top");
        bottom = location -> new MCResourceLocation(location.getNamespace(), location.getPath() + "_bottom");
        sides = location -> new MCResourceLocation(location.getNamespace(), location.getPath() + "_sides");
    }
    
    
    public MCResourceLocation getTop(MCResourceLocation stairsName) {
        return top.apply(stairsName);
    }
    
    public MCResourceLocation getBottom(MCResourceLocation stairsName) {
        return bottom.apply(stairsName);
    }
    
    public MCResourceLocation getSides(MCResourceLocation stairsName) {
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
    public BlockBuilderStairs withTopTexture(MCResourceLocation topTexture) {
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
    public BlockBuilderStairs withBottomTexture(MCResourceLocation bottomTexture) {
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
    public BlockBuilderStairs withSidesTexture(MCResourceLocation sidesTexture) {
        this.top = ignored -> sidesTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the top side should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the end texture for it.
     *
     * @param topTexture The function to use
     * @docParam endTexture (blockName as MCResourceLocation) => new MCResourceLocation(blockName.namespace, blockName.path + "_top")
     * @return This builder, used for method chaining
     */
    @ZenCodeType.Method
    public BlockBuilderStairs withTopTexture(Function<MCResourceLocation, MCResourceLocation> topTexture) {
        this.top = topTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the bottom side should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the end texture for it.
     *
     * @param bottomTexture The function to use
     * @docParam bottomTexture (blockName as MCResourceLocation) => new MCResourceLocation(blockName.namespace, blockName.path + "_bottom")
     * @return This builder, used for method chaining
     */
    @ZenCodeType.Method
    public BlockBuilderStairs withBottomTexture(Function<MCResourceLocation, MCResourceLocation> bottomTexture) {
        this.bottom = bottomTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the sides should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the end texture for it.
     *
     * @param sidesTexture The function to use
     * @docParam sidesTexture (blockName as MCResourceLocation) => new MCResourceLocation(blockName.namespace, blockName.path + "_sides")
     * @return This builder, used for method chaining
     */
    @ZenCodeType.Method
    public BlockBuilderStairs withSidesTexture(Function<MCResourceLocation, MCResourceLocation> sidesTexture) {
        this.sides = sidesTexture;
        return this;
    }
    
    @Override
    public void build(MCResourceLocation location) {
        VanillaFactory.registerBlock(new CoTStairsBlock(this, location));
    }
}
