package com.blamejared.contenttweaker.blocks.render;

import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.BlockRenderType")
@Document("mods/contenttweaker/API/block/BlockRenderType")
public enum BlockRenderType {
    /**
     * Solid, opaque. This is the default value.
     */
    @ZenCodeType.Field
    SOLID,

    /**
     * Translucent, like vanilla ice and water
     */
    @ZenCodeType.Field
    TRANSLUCENT,

    /**
     * Transparent, often used for non-full blocks, like saplings and beds.
     */
    @ZenCodeType.Field
    CUTOUT,

    /**
     * Transparent, but mip mapped, like vanilla glass.
     *
     * @link {https://en.wikipedia.org/wiki/Mipmap} for more info.
     */
    @ZenCodeType.Field
    CUTOUT_MIPPED;

    @OnlyIn(Dist.CLIENT)
    private RenderType getVanillaRenderType() {
        switch (this) {
            case TRANSLUCENT:
                return RenderType.getTranslucent();
            case CUTOUT:
                return RenderType.getCutout();
            case CUTOUT_MIPPED:
                return RenderType.getCutoutMipped();
            default:
                return RenderType.getSolid();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void registerToBlock(IIsCoTBlock block) {
        RenderTypeLookup.setRenderLayer(block.getBlock(), this.getVanillaRenderType());
    }

    public boolean notSolid() {
        return this != SOLID;
    }
}
