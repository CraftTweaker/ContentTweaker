package com.teamacronymcoders.contenttweaker.renderers;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

@SideOnly(Side.CLIENT)
public class EmptyModel implements IModel {
    private Collection<ResourceLocation> collection = new ArrayList<>();

    @Override
    @Nonnull
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableList.copyOf(collection);
    }

    @Override
    @Nonnull
    public Collection<ResourceLocation> getTextures() {
        return ImmutableList.copyOf(collection);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new EmptyBakedModel();
    }

    @Override
    @Nonnull
    public IModelState getDefaultState() {
        return new EmptyModelState();
    }
}
