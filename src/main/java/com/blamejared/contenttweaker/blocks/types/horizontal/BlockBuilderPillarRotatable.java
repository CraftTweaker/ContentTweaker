package com.blamejared.contenttweaker.blocks.types.horizontal;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.util.*;
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
    
    private Function<ResourceLocation, ResourceLocation> end;
    private Function<ResourceLocation, ResourceLocation> sides;
    
    public BlockBuilderPillarRotatable(BlockBuilder blockBuilder) {
        super(blockBuilder);
        end = location -> new ResourceLocation(location.getNamespace(), location.getPath() + "_end");
        sides = location -> new ResourceLocation(location.getNamespace(), location.getPath() + "_sides");
    }
    
    public ResourceLocation getEnd(ResourceLocation name) {
        return end.apply(name);
    }
    
    public ResourceLocation getSides(ResourceLocation name) {
        return sides.apply(name);
    }
    
    /**
     * Allows you to override the path of the texture that the end sides (top/bottom) should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     *
     * @param endTexture The texture to be used for the end sides.
     * @return This builder, used for method chaining
     * @docParam endTexture <resource:contenttweaker:my_awesome_pillar_end>
     */
    @ZenCodeType.Method
    public BlockBuilderPillarRotatable withEndTexture(ResourceLocation endTexture) {
        this.end = ignored -> endTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the sides (everything but top/bottom) should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     *
     * @param sidesTexture The texture to be used for the sides.
     * @return This builder, used for method chaining
     * @docParam sidesTexture <resource:contenttweaker:my_awesome_pillar_side>
     */
    @ZenCodeType.Method
    public BlockBuilderPillarRotatable withSideTexture(ResourceLocation sidesTexture) {
        this.sides = ignored -> sidesTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the end sides (top/bottom) should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the end texture for it.
     *
     * @param endTexture The function to use
     * @return This builder, used for method chaining
     * @docParam endTexture (blockName as ResourceLocation) => new ResourceLocation(blockName.namespace, blockName.path + "_end")
     */
    @ZenCodeType.Method
    public BlockBuilderPillarRotatable withEndTexture(Function<ResourceLocation, ResourceLocation> endTexture) {
        this.end = endTexture;
        return this;
    }
    
    /**
     * Allows you to override the path of the texture that the sides (everything but top/bottom) should use.
     * If that texture's namespace is in the namespace of CoT or any of its addons (that support it) then the image will be created by default.
     * Uses a function that takes the block's name as input and returns the side texture for it.
     *
     * @param sidesTexture The function to use
     * @return This builder, used for method chaining
     * @docParam sidesTexture (blockName as ResourceLocation) => new ResourceLocation(blockName.namespace, blockName.path + "_sides")
     */
    @ZenCodeType.Method
    public BlockBuilderPillarRotatable withSideTexture(Function<ResourceLocation, ResourceLocation> sidesTexture) {
        this.sides = sidesTexture;
        return this;
    }
    
    @Override
    public void build(ResourceLocation location) {
        CoTBlockRotatablePillar blockRotatablePillar = new CoTBlockRotatablePillar(this, location);
        VanillaFactory.queueBlockForRegistration(blockRotatablePillar, this.blockBuilder.getRenderType());
    }
    
}
