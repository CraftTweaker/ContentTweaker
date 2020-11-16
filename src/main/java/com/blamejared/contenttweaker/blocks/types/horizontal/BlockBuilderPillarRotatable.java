package com.blamejared.contenttweaker.blocks.types.horizontal;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.*;

import java.util.function.*;

/**
 * A special Block Builder that allows you to create blocks that can be rotated in the same way as logs can.
 * <p>
 * This means that it has one texture for the top and bottom and one texture for the sides.
 * By default these sides' locations are the block's name, followed by and either `_end` or `sides`.
 * As with most things here, sample images are generated for you by default, though.
 *
 * @docParam this new BlockBuilder().withType<BlockBuilderPillarRotatable>()
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.pillar.BlockBuilderPillarRotatable")
@Document("mods/contenttweaker/API/block/pillar/BlockBuilderPillarRotatable")
public class BlockBuilderPillarRotatable extends BlockTypeBuilder {
    
    private Function<MCResourceLocation, MCResourceLocation> end;
    private Function<MCResourceLocation, MCResourceLocation> sides;
    
    public BlockBuilderPillarRotatable(BlockBuilder blockBuilder) {
        super(blockBuilder);
        end = location -> new MCResourceLocation(location.getNamespace(), location.getPath() + "_end");
        sides = location -> new MCResourceLocation(location.getNamespace(), location.getPath() + "_sides");
    }
    
    public MCResourceLocation getEnd(MCResourceLocation name) {
        return end.apply(name);
    }
    
    public MCResourceLocation getSides(MCResourceLocation name) {
        return sides.apply(name);
    }
    
    /**
     * Allows you to override the path of the texture that the end sides (top/bottom) should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     *
     * @param endTexture The texture to be used for the end sides.
     * @docParam endTexture <resource:contenttweaker:my_awesome_pillar_end>
     * @return This builder, used for method chaining
     */
    @ZenCodeType.Method
    public BlockBuilderPillarRotatable withEndTexture(MCResourceLocation endTexture) {
        this.end = ignored -> endTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the sides (everything but top/bottom) should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     *
     * @param sidesTexture The texture to be used for the sides.
     * @docParam sidesTexture <resource:contenttweaker:my_awesome_pillar_side>
     * @return This builder, used for method chaining
     */
    @ZenCodeType.Method
    public BlockBuilderPillarRotatable withSideTexture(MCResourceLocation sidesTexture) {
        this.sides = ignored -> sidesTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the end sides (top/bottom) should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the end texture for it.
     *
     * @param endTexture The function to use
     * @docParam endTexture (blockName as MCResourceLocation) => new MCResourceLocation(blockName.namespace, blockName.path + "_end")
     * @return This builder, used for method chaining
     */
    @ZenCodeType.Method
    public BlockBuilderPillarRotatable withEndTexture(Function<MCResourceLocation, MCResourceLocation> endTexture) {
        this.end = endTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the sides (everything but top/bottom) should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the side texture for it.
     *
     * @param sidesTexture The function to use
     * @docParam sidesTexture (blockName as MCResourceLocation) => new MCResourceLocation(blockName.namespace, blockName.path + "_sides")
     * @return This builder, used for method chaining
     */
    @ZenCodeType.Method
    public BlockBuilderPillarRotatable withSideTexture(Function<MCResourceLocation, MCResourceLocation> sidesTexture) {
        this.sides = sidesTexture;
        return this;
    }
    
    @Override
    public void build(MCResourceLocation location) {
        VanillaFactory.queueBlockForRegistration(new CoTBlockRotatablePillar(this, location));
    }
    
}
